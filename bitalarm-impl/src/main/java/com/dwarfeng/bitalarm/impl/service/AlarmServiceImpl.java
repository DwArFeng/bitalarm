package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.handler.AlarmHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmHandler alarmHandler;
    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    public void processAlarm(long pointId, byte[] data, Date happenedDate) throws ServiceException {
        try {
            alarmHandler.processAlarm(pointId, data, happenedDate);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("处理报警信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
