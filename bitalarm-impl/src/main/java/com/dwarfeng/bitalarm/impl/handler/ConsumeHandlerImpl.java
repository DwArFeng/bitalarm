package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.handler.ConsumeHandler;
import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ConsumeHandlerImpl<E extends Bean> implements ConsumeHandler<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeHandlerImpl.class);

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final List<ConsumeTask<E>> processingConsumeTasks;
    private final List<ConsumeTask<E>> endingConsumeTasks;
    private final Consumer<E> consumer;
    private int thread;

    private final Lock lock = new ReentrantLock();
    private final ConsumeBuffer<E> consumeBuffer = new ConsumeBuffer<>();
    private boolean startFlag = false;

    public ConsumeHandlerImpl(
            @NonNull ThreadPoolTaskExecutor threadPoolTaskExecutor,
            @NonNull List<ConsumeTask<E>> processingConsumeTasks,
            @NonNull List<ConsumeTask<E>> endingConsumeTasks,
            @NonNull Consumer<E> consumer,
            int thread) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.processingConsumeTasks = processingConsumeTasks;
        this.endingConsumeTasks = endingConsumeTasks;
        this.consumer = consumer;
        this.thread = Math.max(thread, 1);
    }

    @Override
    public boolean isStart() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() {
        lock.lock();
        try {
            if (!startFlag) {
                LOGGER.info("Consumer handler 开启消费线程...");
                consumeBuffer.block();
                for (int i = 0; i < thread; i++) {
                    ConsumeTask<E> consumeTask = new ConsumeTask<>(consumeBuffer, consumer);
                    threadPoolTaskExecutor.execute(consumeTask);
                    processingConsumeTasks.add(consumeTask);
                }
                startFlag = true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {
        lock.lock();
        try {
            if (startFlag) {
                LOGGER.info("Consume handler 结束消费线程...");
                processingConsumeTasks.forEach(ConsumeTask::shutdown);
                endingConsumeTasks.addAll(processingConsumeTasks);
                processingConsumeTasks.clear();
                consumeBuffer.unblock();
                List<E> element2Consume;
                while (!(element2Consume = consumeBuffer.poll()).isEmpty()) {
                    try {
                        LOGGER.info("消费 consume handler 中剩余的元素 " + element2Consume.size() + " 个...");
                        consumer.consume(element2Consume);
                    } catch (Exception e) {
                        LOGGER.warn("消费元素时发生异常, 最多抛弃 " + element2Consume.size() + " 个元素", e);
                    }
                }
                endingConsumeTasks.removeIf(AbstractTask::isFinished);
                if (!endingConsumeTasks.isEmpty()) {
                    LOGGER.info("Consume handler 中的线程还未完全结束, 等待线程结束...");
                    endingConsumeTasks.forEach(
                            task -> {
                                try {
                                    task.awaitFinish();
                                } catch (InterruptedException ignored) {
                                }
                            }
                    );
                }
                processingConsumeTasks.clear();
                endingConsumeTasks.clear();
                LOGGER.info("Consume handler 已经妥善处理数据, 消费线程结束");
                startFlag = false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void accept(E element) {
        consumeBuffer.accept(element);
    }

    @Override
    public int getBufferSize() {
        return consumeBuffer.getBufferSize();
    }

    @Override
    public int getBatchSize() {
        return consumeBuffer.getBatchSize();
    }

    @Override
    public long getMaxIdleTime() {
        return consumeBuffer.getMaxIdleTime();
    }

    @Override
    public void setBufferParameters(int bufferSize, int batchSize, long maxIdleTime) {
        consumeBuffer.setBufferParameters(bufferSize, batchSize, maxIdleTime);
    }

    @Override
    public int getThread() {
        lock.lock();
        try {
            return thread;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setThread(int thread) {
        lock.lock();
        try {
            thread = Math.max(thread, 1);
            int delta = thread - this.thread;
            this.thread = thread;
            if (startFlag) {
                if (delta > 0) {
                    for (int i = 0; i < delta; i++) {
                        ConsumeTask<E> consumeTask = new ConsumeTask<>(consumeBuffer, consumer);
                        threadPoolTaskExecutor.execute(consumeTask);
                        processingConsumeTasks.add(consumeTask);
                    }
                } else if (delta < 0) {
                    endingConsumeTasks.removeIf(AbstractTask::isFinished);
                    for (int i = 0; i < -delta; i++) {
                        ConsumeTask<E> consumeTask = processingConsumeTasks.remove(0);
                        consumeTask.shutdown();
                        endingConsumeTasks.add(consumeTask);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isIdle() {
        lock.lock();
        try {
            if (consumeBuffer.bufferedSize() > 0) {
                return false;
            }
            if (!processingConsumeTasks.isEmpty()) {
                return false;
            }
            endingConsumeTasks.removeIf(AbstractTask::isFinished);
            return endingConsumeTasks.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    private static final class ConsumeTask<E extends Bean> extends AbstractTask {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeTask.class);

        private final ConsumeBuffer<E> consumeBuffer;
        private final Consumer<E> consumer;
        private final AtomicBoolean runningFlag = new AtomicBoolean(true);

        public ConsumeTask(ConsumeBuffer<E> consumeBuffer, Consumer<E> consumer) {
            this.consumeBuffer = consumeBuffer;
            this.consumer = consumer;
        }

        @Override
        protected void todo() {
            while (runningFlag.get()) {
                List<E> pollList = null;
                try {
                    pollList = consumeBuffer.poll();
                    if (!pollList.isEmpty()) {
                        consumer.consume(pollList);
                    }
                } catch (Exception e) {
                    if (Objects.nonNull(pollList)) {
                        LOGGER.warn("消费元素时发生异常, 最多抛弃 " + pollList.size() + " 个元素", e);
                    }
                }
            }
            LOGGER.info("消费线程退出...");
        }

        public void shutdown() {
            runningFlag.set(false);
        }
    }

    private static class ConsumeBuffer<E extends Bean> {

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<E> buffer = new ArrayList<>();

        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;

        private boolean blockEnabled = true;

        private long lastIdleCheckDate = System.currentTimeMillis();

        public void accept(E element) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize) {
                    try {
                        provideCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                buffer.add(element);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public List<E> poll() {
            long currentTimeMillis = System.currentTimeMillis();
            long timeOffset = 0;
            lock.lock();
            try {
                /*
                 * 线程阻塞的逻辑。
                 *   最终的效果是达到如下的目的。
                 *     [最大空闲时间]或者[批处理]这两个参数有一个小于等于0，意思是只要[buffer]中有至少一个元素，就立即处理。
                 *     除此之外([最大空闲时间]和[批处理]两个参数都大于0)
                 *       当[buffer]的数量小于[批处理]的数且([当前时间]-[最近检查日期] < [最大空闲时间])时，一直阻塞。
                 *     以上条件发生的前提是 [runningFlag] 必须为 true，一旦 [runningFlag] 为 false，则其余参数为任何值都
                 *     不能够阻塞。
                 * 三者要同时满足:
                 *   1. [buffer]中没有任何元素，或者[批处理]大于0并且[buffer]中的元素小于[批处理]中的元素。
                 *      解释: [buffer]中没有任何元素，肯定是要阻塞的。
                 *            [批处理]小于等于0的意思是只要[buffer]中有一个元素，就不能阻塞，因为"buffer.isEmpty()"的结果
                 *            为false已经判断了[buffer]中至少含有一个元素，因此该处逻辑只需判断[批处理]小于等于0。
                 *            [批处理]大于0，且[buffer]中元素的数量小于[批处理]的数量的话，是需要阻塞的。由于前条语句
                 *            "buffer.isEmpty() || batchSize <= 0"的结果为false已经判断了[buffer]中至少含有一个元素且[批处理]
                 *            大于0，此时需要判断[buffer]是否小于[批处理]，如果小于批处理，则阻塞。
                 *   2. [空闲时间]小于[最大空闲时间]，或者[最大空闲时间]小于等于0且buffer中没有任何元素。
                 *      解释: 定义[空闲时间] = [当前时间] - [最近检查日期]
                 *            这部分逻辑比较简单，依照定义给出逻辑判断式。
                 *   3. [runningFlag] 必须为 true。
                 *      解释: 这部分逻辑比较简单，依照定义给出逻辑判断式。
                 */
                while ((buffer.isEmpty() || batchSize <= 0 || buffer.size() < batchSize)
                        //注意: 以下两行是一句
                        && (maxIdleTime <= 0 && buffer.isEmpty() || maxIdleTime > 0
                        && (timeOffset = maxIdleTime - currentTimeMillis + lastIdleCheckDate) > 0)
                        //注意: 以上两行是一句
                        && blockEnabled
                ) {
                    try {
                        if (batchSize <= 1 || maxIdleTime <= 0) {
                            consumeCondition.await();
                        } else {
                            consumeCondition.await(timeOffset, TimeUnit.MILLISECONDS);
                        }
                    } catch (InterruptedException ignored) {
                    }
                    currentTimeMillis = System.currentTimeMillis();
                }

                // 更新最新空闲检查时间。
                lastIdleCheckDate = currentTimeMillis;
                // 取出最多[批处理]个数个元素，如果[buffer]中的元素没有这么多，则全部取出。
                int processingElementSize = Math.min(batchSize, buffer.size());
                List<E> subList = buffer.subList(0, processingElementSize);
                List<E> elements2Return = new ArrayList<>(subList);
                subList.clear();

                provideCondition.signalAll();
                return elements2Return;
            } finally {
                lock.unlock();
            }
        }

        public int bufferedSize() {
            lock.lock();
            try {
                return buffer.size();
            } finally {
                lock.unlock();
            }
        }

        public int getBufferSize() {
            lock.lock();
            try {
                return bufferSize;
            } finally {
                lock.unlock();
            }
        }

        public int getBatchSize() {
            lock.lock();
            try {
                return batchSize;
            } finally {
                lock.unlock();
            }
        }

        public long getMaxIdleTime() {
            lock.lock();
            try {
                return maxIdleTime;
            } finally {
                lock.unlock();
            }
        }

        public void setBufferParameters(int bufferSize, int batchSize, long maxIdleTime) {
            lock.lock();
            try {
                this.bufferSize = Math.max(bufferSize, 1);
                this.batchSize = batchSize;
                this.maxIdleTime = maxIdleTime;

                provideCondition.signalAll();
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void block() {
            lock.lock();
            try {
                this.blockEnabled = true;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void unblock() {
            lock.lock();
            try {
                this.blockEnabled = false;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
