package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.cache.AlarmHistoryCache;
import com.dwarfeng.bitalarm.stack.dao.AlarmHistoryDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlarmHistoryCrudOperation implements CrudOperation<LongIdKey, AlarmHistory> {

    @Autowired
    private AlarmHistoryDao alarmHistoryDao;
    @Autowired
    private AlarmHistoryCache alarmHistoryCache;

    @Value("${cache.timeout.entity.alarm_history}")
    private long alarmHistoryTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return alarmHistoryCache.exists(key) || alarmHistoryDao.exists(key);
    }

    @Override
    public AlarmHistory get(LongIdKey key) throws Exception {
        if (alarmHistoryCache.exists(key)) {
            return alarmHistoryCache.get(key);
        } else {
            if (!alarmHistoryDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            AlarmHistory alarmHistory = alarmHistoryDao.get(key);
            alarmHistoryCache.push(alarmHistory, alarmHistoryTimeout);
            return alarmHistory;
        }
    }

    @Override
    public LongIdKey insert(AlarmHistory alarmHistory) throws Exception {
        alarmHistoryCache.push(alarmHistory, alarmHistoryTimeout);
        return alarmHistoryDao.insert(alarmHistory);
    }

    @Override
    public void update(AlarmHistory alarmHistory) throws Exception {
        alarmHistoryCache.push(alarmHistory, alarmHistoryTimeout);
        alarmHistoryDao.update(alarmHistory);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        alarmHistoryDao.delete(key);
        alarmHistoryCache.delete(key);
    }
}
