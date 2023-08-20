package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.handler.*;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerId;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerStatus;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class AlarmHandlerImpl implements AlarmHandler {

    private final GeneralStartableHandler startableHandler;
    private final AlarmLocalCacheHandler alarmLocalCacheHandler;
    private final ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler;
    private final ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler;
    private final ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler;

    private final AlarmProcessor alarmProcessor;

    private final Lock lock = new ReentrantLock();
    private final Map<ConsumerId, ConsumeHandler<? extends Bean>> consumeHandlerMap = new EnumMap<>(ConsumerId.class);

    public AlarmHandlerImpl(
            AlarmWorker alarmWorker,
            AlarmLocalCacheHandler alarmLocalCacheHandler,
            @Qualifier("alarmUpdatedEventConsumeHandler")
            ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler,
            @Qualifier("historyRecordedEventConsumeHandler")
            ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler,
            @Qualifier("alarmHistoryValueConsumeHandler")
            ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler,
            AlarmProcessor alarmProcessor
    ) {
        this.startableHandler = new GeneralStartableHandler(alarmWorker);
        this.alarmLocalCacheHandler = alarmLocalCacheHandler;
        this.alarmUpdatedEventConsumeHandler = alarmUpdatedEventConsumeHandler;
        this.historyRecordedEventConsumeHandler = historyRecordedEventConsumeHandler;
        this.alarmHistoryValueConsumeHandler = alarmHistoryValueConsumeHandler;
        this.alarmProcessor = alarmProcessor;
    }

    @PostConstruct
    public void init() {
        lock.lock();
        try {
            consumeHandlerMap.put(ConsumerId.EVENT_ALARM, alarmUpdatedEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.EVENT_HISTORY, historyRecordedEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_HISTORY, alarmHistoryValueConsumeHandler);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void processAlarm(LongIdKey pointKey, byte[] data, Date happenedDate) throws HandlerException {
        lock.lock();
        try {
            alarmProcessor.processAlarm(pointKey, data, happenedDate);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<AlarmSetting> getAlarmSetting(LongIdKey pointKey) throws HandlerException {
        lock.lock();
        try {
            return alarmLocalCacheHandler.get(pointKey);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clearLocalCache() throws HandlerException {
        lock.lock();
        try {
            alarmLocalCacheHandler.clear();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws HandlerException {
        lock.lock();
        try {
            ConsumeHandler<? extends Bean> consumeHandler = consumeHandlerMap.get(consumerId);
            return new ConsumerStatus(
                    consumeHandler.bufferedSize(),
                    consumeHandler.getBufferSize(),
                    consumeHandler.getBatchSize(),
                    consumeHandler.getMaxIdleTime(),
                    consumeHandler.getThread(),
                    consumeHandler.isIdle()
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread
    ) throws HandlerException {
        lock.lock();
        try {
            ConsumeHandler<? extends Bean> consumeHandler = consumeHandlerMap.get(consumerId);
            consumeHandler.setBufferParameters(
                    Objects.isNull(bufferSize) ? consumeHandler.getBufferSize() : bufferSize,
                    Objects.isNull(batchSize) ? consumeHandler.getBatchSize() : batchSize,
                    Objects.isNull(maxIdleTime) ? consumeHandler.getMaxIdleTime() : maxIdleTime
            );
            consumeHandler.setThread(
                    Objects.isNull(thread) ? consumeHandler.getThread() : thread
            );
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static class AlarmWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(AlarmWorker.class);

        private final SourceHandler sourceHandler;
        private final ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler;
        private final ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler;
        private final ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler;

        private final AlarmProcessor alarmProcessor;

        public AlarmWorker(
                SourceHandler sourceHandler,
                @Qualifier("alarmUpdatedEventConsumeHandler")
                ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler,
                @Qualifier("historyRecordedEventConsumeHandler")
                ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler,
                @Qualifier("alarmHistoryValueConsumeHandler")
                ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler,
                AlarmProcessor alarmProcessor
        ) {
            this.sourceHandler = sourceHandler;
            this.alarmUpdatedEventConsumeHandler = alarmUpdatedEventConsumeHandler;
            this.historyRecordedEventConsumeHandler = historyRecordedEventConsumeHandler;
            this.alarmHistoryValueConsumeHandler = alarmHistoryValueConsumeHandler;
            this.alarmProcessor = alarmProcessor;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("消费处理器启动...");
            alarmUpdatedEventConsumeHandler.start();
            historyRecordedEventConsumeHandler.start();
            alarmHistoryValueConsumeHandler.start();

            LOGGER.info("报警处理器启动...");
            alarmProcessor.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("数据源上线...");
            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.online();
            }
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("数据源下线...");
            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.offline();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("报警处理器关闭...");
            alarmProcessor.stop();

            LOGGER.info("消费处理器关闭...");
            alarmUpdatedEventConsumeHandler.stop();
            historyRecordedEventConsumeHandler.stop();
            alarmHistoryValueConsumeHandler.stop();
        }
    }
}
