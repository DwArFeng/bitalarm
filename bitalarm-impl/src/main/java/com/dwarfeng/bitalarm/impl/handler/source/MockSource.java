package com.dwarfeng.bitalarm.impl.handler.source;

import com.dwarfeng.bitalarm.impl.handler.Source;
import com.dwarfeng.bitalarm.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.bitalarm.stack.service.AlarmService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MockSource implements Source {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockSource.class);
    private static final int BIT_PER_BYTE = 8;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    @Qualifier("mockSource.random")
    private Random random;

    @Value("${source.mock.alarm_interval}")
    private long alarmInterval;
    @Value("${source.mock.point_id}")
    private long pointId;
    @Value("${source.mock.bit_size}")
    private int bitSize;

    private final Lock lock = new ReentrantLock();
    private boolean startFlag = false;
    private MockAlarmPlain mockAlarmPlain = null;
    private ScheduledFuture<?> mockAlarmPlainFuture = null;

    @Override
    public boolean isOnline() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void online() throws HandlerException {
        lock.lock();
        try {
            if (!startFlag) {
                LOGGER.info("Mock source 上线...");
                mockAlarmPlain = new MockAlarmPlain(alarmService, pointId, bitSize2ByteSize(bitSize), random);
                mockAlarmPlainFuture = scheduler.scheduleAtFixedRate(mockAlarmPlain, alarmInterval);
                startFlag = true;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            if (startFlag) {
                LOGGER.info("Mock source 下线...");
                mockAlarmPlain.shutdown();
                mockAlarmPlainFuture.cancel(true);
                mockAlarmPlain = null;
                mockAlarmPlainFuture = null;
                startFlag = false;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
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

        private final AlarmService alarmService;
        private final long pointId;
        private final int byteSize;
        private final Random random;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        public MockAlarmPlain(AlarmService alarmService, long pointId, int byteSize, Random random) {
            this.alarmService = alarmService;
            this.pointId = pointId;
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
                    alarmService.processAlarm(pointId, bytes, new Date());
                } catch (ServiceException e) {
                    if (e.getCode().getCode() == ServiceExceptionCodes.ALARM_HANDLER_DISABLED.getCode()) {
                        LOGGER.warn("记录处理器被禁用, 数据点 " + pointId +
                                ", 数据 " + Arrays.toString(bytes) + " 将会被忽略", e);
                    } else {
                        LOGGER.warn("记录处理器无法处理, 数据点 " + pointId +
                                ", 数据 " + Arrays.toString(bytes) + " 将会被忽略", e);
                    }
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
