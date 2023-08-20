package com.dwarfeng.bitalarm.impl.cache;

import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.cache.AlarmSettingCache;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AlarmSettingCacheImpl implements AlarmSettingCache {

    private final RedisBatchBaseCache<LongIdKey, AlarmSetting, FastJsonAlarmSetting> alarmSettingBatchBaseDelegate;

    public AlarmSettingCacheImpl(
            RedisBatchBaseCache<LongIdKey, AlarmSetting, FastJsonAlarmSetting> alarmSettingBatchBaseDelegate
    ) {
        this.alarmSettingBatchBaseDelegate = alarmSettingBatchBaseDelegate;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws CacheException {
        return alarmSettingBatchBaseDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public AlarmSetting get(LongIdKey key) throws CacheException {
        return alarmSettingBatchBaseDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void push(AlarmSetting value, long timeout) throws CacheException {
        alarmSettingBatchBaseDelegate.push(value, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws CacheException {
        alarmSettingBatchBaseDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        alarmSettingBatchBaseDelegate.clear();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(List<LongIdKey> keys) throws CacheException {
        return alarmSettingBatchBaseDelegate.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(List<LongIdKey> keys) throws CacheException {
        return alarmSettingBatchBaseDelegate.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<AlarmSetting> batchGet(List<LongIdKey> keys) throws CacheException {
        return alarmSettingBatchBaseDelegate.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchPush(List<AlarmSetting> entities, long timeout) throws CacheException {
        alarmSettingBatchBaseDelegate.batchPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(List<LongIdKey> keys) throws CacheException {
        alarmSettingBatchBaseDelegate.batchDelete(keys);
    }
}
