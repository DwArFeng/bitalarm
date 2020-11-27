package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.impl.handler.Source;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.handler.AlarmHandler;
import com.dwarfeng.bitalarm.stack.handler.AlarmLocalCacheHandler;
import com.dwarfeng.bitalarm.stack.handler.ConsumeHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AlarmQosServiceImpl implements AlarmQosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmQosServiceImpl.class);

    @Autowired
    private AlarmLocalCacheHandler alarmLocalCacheHandler;
    @Autowired(required = false)
    @SuppressWarnings("FieldMayBeFinal")
    private List<Source> sources = new ArrayList<>();
    @Autowired
    private AlarmHandler alarmHandler;
    @Autowired
    @Qualifier("alarmUpdatedEventConsumeHandler")
    private ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler;
    @Autowired
    @Qualifier("alarmInfoValueConsumeHandler")
    private ConsumeHandler<AlarmInfo> alarmInfoValueConsumeHandler;
    @Autowired
    @Qualifier("historyRecordedEventConsumeHandler")
    private ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler;
    @Autowired
    @Qualifier("alarmHistoryValueConsumeHandler")
    private ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();
    private final Map<ConsumerId, ConsumeHandler<? extends Bean>> consumeHandlerMap = new EnumMap<>(ConsumerId.class);

    @PostConstruct
    public void init() {
        lock.lock();
        try {
            consumeHandlerMap.put(ConsumerId.EVENT_ALARM, alarmUpdatedEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_ALARM, alarmInfoValueConsumeHandler);
            consumeHandlerMap.put(ConsumerId.EVENT_HISTORY, historyRecordedEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_HISTORY, alarmHistoryValueConsumeHandler);
        } finally {
            lock.unlock();
        }
    }

    @PreDestroy
    public void dispose() throws Exception {
        lock.lock();
        try {
            internalStopAlarm();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<AlarmSetting> getAlarmSetting(LongIdKey pointKey) throws ServiceException {
        lock.lock();
        try {
            return alarmLocalCacheHandler.getAlarmSetting(pointKey);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logAndThrow("从本地缓存中获取记录上下文时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            alarmLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除本地缓存时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException {
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
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取消费者状态时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException {
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
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("设置消费者参数时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void startAlarm() throws ServiceException {
        lock.lock();
        try {
            LOGGER.info("开启记录服务...");
            alarmUpdatedEventConsumeHandler.start();
            alarmInfoValueConsumeHandler.start();
            historyRecordedEventConsumeHandler.start();
            alarmHistoryValueConsumeHandler.start();

            alarmHandler.start();

            for (Source source : sources) {
                source.online();
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("开启记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void stopAlarm() throws ServiceException {
        lock.lock();
        try {
            internalStopAlarm();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("关闭记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    private void internalStopAlarm() throws HandlerException {
        LOGGER.info("关闭记录服务...");
        for (Source source : sources) {
            source.offline();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        alarmHandler.stop();

        alarmUpdatedEventConsumeHandler.stop();
        alarmInfoValueConsumeHandler.stop();
        historyRecordedEventConsumeHandler.stop();
        alarmHistoryValueConsumeHandler.stop();
    }
}
