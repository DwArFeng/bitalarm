package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.dao.AlarmHistoryDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlarmHistoryCrudOperation implements BatchCrudOperation<LongIdKey, AlarmHistory> {

    @Autowired
    private AlarmHistoryDao alarmHistoryDao;

    @Value("${cache.timeout.entity.alarm_history}")
    private long alarmHistoryTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return alarmHistoryDao.exists(key);
    }

    @Override
    public AlarmHistory get(LongIdKey key) throws Exception {
        if (!alarmHistoryDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return alarmHistoryDao.get(key);
    }

    @Override
    public LongIdKey insert(AlarmHistory alarmHistory) throws Exception {
        return alarmHistoryDao.insert(alarmHistory);
    }

    @Override
    public void update(AlarmHistory alarmHistory) throws Exception {
        alarmHistoryDao.update(alarmHistory);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        alarmHistoryDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return alarmHistoryDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return alarmHistoryDao.nonExists(keys);
    }

    @Override
    public List<AlarmHistory> batchGet(List<LongIdKey> keys) throws Exception {
        if (!alarmHistoryDao.allExists(keys)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return alarmHistoryDao.batchGet(keys);
    }

    @Override
    public List<LongIdKey> batchInsert(List<AlarmHistory> elements) throws Exception {
        return alarmHistoryDao.batchInsert(elements);
    }

    @Override
    public void batchUpdate(List<AlarmHistory> elements) throws Exception {
        alarmHistoryDao.batchUpdate(elements);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        alarmHistoryDao.batchDelete(keys);
    }
}
