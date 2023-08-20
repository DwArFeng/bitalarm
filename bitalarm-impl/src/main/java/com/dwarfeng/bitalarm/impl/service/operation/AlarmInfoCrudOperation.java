package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.dao.AlarmInfoDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlarmInfoCrudOperation implements BatchCrudOperation<LongIdKey, AlarmInfo> {

    private final AlarmInfoDao AlarmInfoDao;

    public AlarmInfoCrudOperation(AlarmInfoDao AlarmInfoDao) {
        this.AlarmInfoDao = AlarmInfoDao;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return AlarmInfoDao.exists(key);
    }

    @Override
    public AlarmInfo get(LongIdKey key) throws Exception {
        if (!AlarmInfoDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return AlarmInfoDao.get(key);
    }

    @Override
    public LongIdKey insert(AlarmInfo AlarmInfo) throws Exception {
        return AlarmInfoDao.insert(AlarmInfo);
    }

    @Override
    public void update(AlarmInfo AlarmInfo) throws Exception {
        AlarmInfoDao.update(AlarmInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        AlarmInfoDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return AlarmInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return AlarmInfoDao.nonExists(keys);
    }

    @Override
    public List<AlarmInfo> batchGet(List<LongIdKey> keys) throws Exception {
        if (!AlarmInfoDao.allExists(keys)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return AlarmInfoDao.batchGet(keys);
    }

    @Override
    public List<LongIdKey> batchInsert(List<AlarmInfo> AlarmInfoDetailCategories) throws Exception {
        return AlarmInfoDao.batchInsert(AlarmInfoDetailCategories);
    }

    @Override
    public void batchUpdate(List<AlarmInfo> AlarmInfoDetailCategories) throws Exception {
        AlarmInfoDao.batchUpdate(AlarmInfoDetailCategories);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
