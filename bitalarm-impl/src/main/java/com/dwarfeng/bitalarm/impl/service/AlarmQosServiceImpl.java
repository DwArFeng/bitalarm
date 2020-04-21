package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.impl.handler.Source;
import com.dwarfeng.bitalarm.stack.bean.dto.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.handler.AlarmHandler;
import com.dwarfeng.bitalarm.stack.handler.AlarmLocalCacheHandler;
import com.dwarfeng.bitalarm.stack.handler.ConsumeHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
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
    @Qualifier("alarmAppearEventConsumerHandler")
    private ConsumeHandler<AlarmInfo> alarmAppearEventConsumerHandler;
    @Autowired
    @Qualifier("alarmDisappearEventConsumerHandler")
    private ConsumeHandler<AlarmInfo> alarmDisappearEventConsumerHandler;
    @Autowired
    @Qualifier("historyRecordEventConsumerHandler")
    private ConsumeHandler<AlarmHistory> historyRecordEventConsumerHandler;
    @Autowired
    @Qualifier("alarmHistoryValueConsumerHandler")
    private ConsumeHandler<AlarmHistory> alarmHistoryValueConsumerHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    @PreDestroy
    public void dispose() throws ServiceException {
        stopAlarm();
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
    public void startAlarm() throws ServiceException {
        lock.lock();
        try {
            LOGGER.info("开启记录服务...");
            alarmAppearEventConsumerHandler.start();
            alarmDisappearEventConsumerHandler.start();
            historyRecordEventConsumerHandler.start();
            alarmHistoryValueConsumerHandler.start();

            alarmHandler.enable();

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
            LOGGER.info("关闭记录服务...");
            for (Source source : sources) {
                source.offline();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            alarmHandler.disable();

            alarmAppearEventConsumerHandler.stop();
            alarmDisappearEventConsumerHandler.stop();
            historyRecordEventConsumerHandler.stop();
            alarmHistoryValueConsumerHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("关闭记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }
}
