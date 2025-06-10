package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.handler.AlarmHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class AlarmQosServiceImpl implements AlarmQosService {

    private final AlarmHandler alarmHandler;
    private final ServiceExceptionMapper sem;

    public AlarmQosServiceImpl(AlarmHandler alarmHandler, ServiceExceptionMapper sem) {
        this.alarmHandler = alarmHandler;
        this.sem = sem;
    }

    @PreDestroy
    public void dispose() throws Exception {
        internalStopAlarm();
    }

    @Override
    public List<AlarmSetting> getAlarmSetting(LongIdKey pointKey) throws ServiceException {
        try {
            return alarmHandler.getAlarmSetting(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定的数据点对应的所有报警设置时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            alarmHandler.clearLocalCache();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException {
        try {
            return alarmHandler.getConsumerStatus(consumerId);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定消费者的消费者状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void setConsumerParameters(ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread) throws ServiceException {
        try {
            alarmHandler.setConsumerParameters(consumerId, bufferSize, batchSize, maxIdleTime, thread);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置指定消费者的参数时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void startAlarm() throws ServiceException {
        try {
            alarmHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("开启记录服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stopAlarm() throws ServiceException {
        try {
            internalStopAlarm();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止记录服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private void internalStopAlarm() throws HandlerException {
        alarmHandler.stop();
    }
}
