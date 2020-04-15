package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.cache.AlarmHistoryCache;
import com.dwarfeng.bitalarm.stack.cache.AlarmSettingCache;
import com.dwarfeng.bitalarm.stack.dao.AlarmHistoryDao;
import com.dwarfeng.bitalarm.stack.dao.AlarmSettingDao;
import com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.CrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlarmSettingCrudOperation implements CrudOperation<LongIdKey, AlarmSetting> {

    @Autowired
    private AlarmSettingDao alarmSettingDao;
    @Autowired
    private AlarmHistoryDao alarmHistoryDao;

    @Autowired
    private AlarmSettingCache alarmSettingCache;
    @Autowired
    private AlarmHistoryCache alarmHistoryCache;

    @Value("${cache.timeout.entity.alarm_setting}")
    private long alarmSettingTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return alarmSettingCache.exists(key) || alarmSettingDao.exists(key);
    }

    @Override
    public AlarmSetting get(LongIdKey key) throws Exception {
        if (alarmSettingCache.exists(key)) {
            return alarmSettingCache.get(key);
        } else {
            if (!alarmSettingDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            AlarmSetting alarmSetting = alarmSettingDao.get(key);
            alarmSettingCache.push(alarmSetting, alarmSettingTimeout);
            return alarmSetting;
        }
    }

    @Override
    public LongIdKey insert(AlarmSetting alarmSetting) throws Exception {
        alarmSettingCache.push(alarmSetting, alarmSettingTimeout);
        return alarmSettingDao.insert(alarmSetting);
    }

    @Override
    public void update(AlarmSetting alarmSetting) throws Exception {
        alarmSettingCache.push(alarmSetting, alarmSettingTimeout);
        alarmSettingDao.update(alarmSetting);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        //删除与点位相关的过滤器触发器。
        {
            //查找点位拥有的过滤器与触发器。
            List<LongIdKey> alarmHistoryKeys = alarmHistoryDao
                    .lookup(AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING, new Object[]{key})
                    .stream().map(AlarmHistory::getKey).collect(Collectors.toList());

            //删除点位拥有的过滤器与触发器。
            alarmHistoryDao.batchDelete(alarmHistoryKeys);
            alarmHistoryCache.batchDelete(alarmHistoryKeys);
        }

        alarmSettingDao.delete(key);
        alarmSettingCache.delete(key);
    }
}
