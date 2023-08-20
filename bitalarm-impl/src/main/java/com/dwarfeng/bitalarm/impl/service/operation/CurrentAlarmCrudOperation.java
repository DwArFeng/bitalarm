package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.bitalarm.stack.dao.CurrentAlarmDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class CurrentAlarmCrudOperation implements CrudOperation<LongIdKey, CurrentAlarm> {

    private final CurrentAlarmDao currentAlarmDao;

    public CurrentAlarmCrudOperation(CurrentAlarmDao currentAlarmDao) {
        this.currentAlarmDao = currentAlarmDao;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return currentAlarmDao.exists(key);
    }

    @Override
    public CurrentAlarm get(LongIdKey key) throws Exception {
        if (!currentAlarmDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return currentAlarmDao.get(key);
    }

    @Override
    public LongIdKey insert(CurrentAlarm currentAlarm) throws Exception {
        return currentAlarmDao.insert(currentAlarm);
    }

    @Override
    public void update(CurrentAlarm currentAlarm) throws Exception {
        currentAlarmDao.update(currentAlarm);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        currentAlarmDao.delete(key);
    }
}
