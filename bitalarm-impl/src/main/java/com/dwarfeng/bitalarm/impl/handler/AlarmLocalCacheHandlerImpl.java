package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.handler.AlarmLocalCacheHandler;
import com.dwarfeng.bitalarm.stack.service.EnabledAlarmSettingLookupService;
import com.dwarfeng.bitalarm.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class AlarmLocalCacheHandlerImpl implements AlarmLocalCacheHandler {

    private final AlarmSettingFetcher alarmSettingFetcher;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<LongIdKey, List<AlarmSetting>> alarmSettingListMap = new HashMap<>();
    private final Set<LongIdKey> notExistPoints = new HashSet<>();

    public AlarmLocalCacheHandlerImpl(AlarmSettingFetcher alarmSettingFetcher) {
        this.alarmSettingFetcher = alarmSettingFetcher;
    }

    @Override
    public List<AlarmSetting> getAlarmSetting(LongIdKey pointKey) throws HandlerException {
        try {
            lock.readLock().lock();
            try {
                if (alarmSettingListMap.containsKey(pointKey)) {
                    return alarmSettingListMap.get(pointKey);
                }
                if (notExistPoints.contains(pointKey)) {
                    return null;
                }
            } finally {
                lock.readLock().unlock();
            }
            lock.writeLock().lock();
            try {
                if (alarmSettingListMap.containsKey(pointKey)) {
                    return alarmSettingListMap.get(pointKey);
                }
                if (notExistPoints.contains(pointKey)) {
                    return null;
                }
                List<AlarmSetting> alarmSettings = alarmSettingFetcher.fetchAlarmSettings(pointKey);
                if (Objects.nonNull(alarmSettings)) {
                    alarmSettingListMap.put(pointKey, alarmSettings);
                    return alarmSettings;
                }
                notExistPoints.add(pointKey);
                return null;
            } finally {
                lock.writeLock().unlock();
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            alarmSettingListMap.clear();
            notExistPoints.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Component
    public static class AlarmSettingFetcher {

        private final PointMaintainService pointMaintainService;
        private final EnabledAlarmSettingLookupService enabledAlarmSettingLookupService;

        public AlarmSettingFetcher(
                PointMaintainService pointMaintainService,
                EnabledAlarmSettingLookupService enabledAlarmSettingLookupService
        ) {
            this.pointMaintainService = pointMaintainService;
            this.enabledAlarmSettingLookupService = enabledAlarmSettingLookupService;
        }

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
        public List<AlarmSetting> fetchAlarmSettings(LongIdKey pointKey) throws Exception {
            if (!pointMaintainService.exists(pointKey)) {
                return null;
            }
            return enabledAlarmSettingLookupService.getEnabledAlarmSettings(pointKey);
        }
    }
}
