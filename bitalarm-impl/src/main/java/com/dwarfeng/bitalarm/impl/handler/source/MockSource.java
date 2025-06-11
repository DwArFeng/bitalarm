package com.dwarfeng.bitalarm.impl.handler.source;

import com.dwarfeng.bitalarm.sdk.handler.source.AbstractSource;
import com.dwarfeng.bitalarm.stack.exception.AlarmDisabledException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Mock 数据源。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockSource.class);
    private static final int BIT_PER_BYTE = 8;

    private final ThreadPoolTaskScheduler scheduler;
    private final Random random;

    @Value("${source.mock.alarm_interval}")
    private long alarmInterval;
    @Value("#{new com.dwarfeng.subgrade.stack.bean.key.LongIdKey('${source.mock.point_id}')}")
    private LongIdKey pointKey;
    @Value("${source.mock.bit_size}")
    private int bitSize;

    private MockAlarmPlain mockAlarmPlain = null;
    private ScheduledFuture<?> mockAlarmPlainFuture = null;

    public MockSource(
            ThreadPoolTaskScheduler scheduler,
            @Qualifier("mockSource.random") Random random
    ) {
        this.scheduler = scheduler;
        this.random = random;
    }

    @Override
    protected void doOnline() {
        LOGGER.info("Mock source 上线...");
        mockAlarmPlain = new MockAlarmPlain(context, pointKey, bitSize2ByteSize(bitSize), random);
        mockAlarmPlainFuture = scheduler.scheduleAtFixedRate(mockAlarmPlain, alarmInterval);
    }

    @Override
    protected void doOffline() {
        LOGGER.info("Mock source 下线...");
        mockAlarmPlain.shutdown();
        mockAlarmPlainFuture.cancel(true);
        mockAlarmPlain = null;
        mockAlarmPlainFuture = null;
    }

    private int bitSize2ByteSize(int bitSize) {
        if (bitSize == 0) {
            return 0;
        }
        if (bitSize % BIT_PER_BYTE == 0) {
            return bitSize / BIT_PER_BYTE;
        }
        return bitSize / BIT_PER_BYTE + 1;
    }

    @Configuration
    public static class MockSourceConfiguration {

        @Value("${source.mock.use_specific_random_seed}")
        private boolean useSpecificRandomSeed;
        @Value("${source.mock.random_seed}")
        private long randomSeed;

        @Bean("mockSource.random")
        public Random random() {
            if (!useSpecificRandomSeed) {
                return new Random();
            } else {
                return new Random(randomSeed);
            }
        }
    }

    private static class MockAlarmPlain implements Runnable {

        private final Context context;
        private final LongIdKey pointKey;
        private final int byteSize;
        private final Random random;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        public MockAlarmPlain(Context context, LongIdKey pointKey, int byteSize, Random random) {
            this.context = context;
            this.pointKey = pointKey;
            this.byteSize = byteSize;
            this.random = random;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                byte[] bytes = null;
                try {
                    bytes = new byte[byteSize];
                    random.nextBytes(bytes);
                    context.processAlarm(pointKey, bytes, new Date());
                } catch (AlarmDisabledException e) {
                    String message = "记录处理器被禁用, 数据点 " + pointKey + ", 数据 " + Arrays.toString(bytes) +
                            " 将会被忽略";
                    LOGGER.warn(message, e);
                } catch (Exception e) {
                    String message = "记录处理器无法处理, 数据点 " + pointKey + ", 数据 " + Arrays.toString(bytes) +
                            " 将会被忽略";
                    LOGGER.warn(message, e);
                }
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }
}
