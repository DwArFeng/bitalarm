package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.handler.AlarmLocalCacheHandler;
import com.dwarfeng.bitalarm.stack.service.EnabledAlarmSettingLookupService;
import com.dwarfeng.bitalarm.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AlarmLocalCacheHandlerImpl implements AlarmLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, List<AlarmSetting>> handler;

    public AlarmLocalCacheHandlerImpl(AlarmSettingFetcher alarmSettingFetcher) {
        this.handler = new GeneralLocalCacheHandler<>(alarmSettingFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public List<AlarmSetting> get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() throws HandlerException {
        handler.clear();
    }

    @Component
    public static class AlarmSettingFetcher implements Fetcher<LongIdKey, List<AlarmSetting>> {

        private final PointMaintainService pointMaintainService;
        private final EnabledAlarmSettingLookupService enabledAlarmSettingLookupService;

        public AlarmSettingFetcher(
                PointMaintainService pointMaintainService,
                EnabledAlarmSettingLookupService enabledAlarmSettingLookupService
        ) {
            this.pointMaintainService = pointMaintainService;
            this.enabledAlarmSettingLookupService = enabledAlarmSettingLookupService;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return pointMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public List<AlarmSetting> fetch(LongIdKey key) throws Exception {
            return enabledAlarmSettingLookupService.getEnabledAlarmSettings(key);
        }
    }
}
