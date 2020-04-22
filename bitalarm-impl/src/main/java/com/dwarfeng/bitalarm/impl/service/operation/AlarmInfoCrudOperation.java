package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.dao.AlarmInfoDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlarmInfoCrudOperation implements CrudOperation<LongIdKey, AlarmInfo> {

    @Autowired
    private AlarmInfoDao alarmInfoDao;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return alarmInfoDao.exists(key);
    }

    @Override
    public AlarmInfo get(LongIdKey key) throws Exception {
        if (!alarmInfoDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return alarmInfoDao.get(key);
    }

    @Override
    public LongIdKey insert(AlarmInfo alarmInfo) throws Exception {
        return alarmInfoDao.insert(alarmInfo);
    }

    @Override
    public void update(AlarmInfo alarmInfo) throws Exception {
        alarmInfoDao.update(alarmInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        alarmInfoDao.delete(key);
    }
}
