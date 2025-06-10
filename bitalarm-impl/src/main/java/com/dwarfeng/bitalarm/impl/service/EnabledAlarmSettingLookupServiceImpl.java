package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.cache.EnabledAlarmSettingCache;
import com.dwarfeng.bitalarm.stack.dao.AlarmSettingDao;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.bitalarm.stack.service.EnabledAlarmSettingLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnabledAlarmSettingLookupServiceImpl implements EnabledAlarmSettingLookupService {

    private final AlarmSettingDao dao;
    private final EnabledAlarmSettingCache cache;
    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_alarm_setting}")
    private long timeout;

    public EnabledAlarmSettingLookupServiceImpl(
            AlarmSettingDao dao,
            EnabledAlarmSettingCache cache,
            ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    public List<AlarmSetting> getEnabledAlarmSettings(LongIdKey pointKey) throws ServiceException {
        try {
            if (cache.exists(pointKey)) {
                return cache.get(pointKey);
            }
            List<AlarmSetting> lookup = dao.lookup(
                    AlarmSettingMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{pointKey}
            );
            cache.set(pointKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询有效的报警设置时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
