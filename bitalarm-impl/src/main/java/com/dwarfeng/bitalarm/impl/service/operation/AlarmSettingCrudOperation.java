package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.cache.AlarmSettingCache;
import com.dwarfeng.bitalarm.stack.cache.EnabledAlarmSettingCache;
import com.dwarfeng.bitalarm.stack.dao.AlarmHistoryDao;
import com.dwarfeng.bitalarm.stack.dao.AlarmInfoDao;
import com.dwarfeng.bitalarm.stack.dao.AlarmSettingDao;
import com.dwarfeng.bitalarm.stack.dao.CurrentAlarmDao;
import com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlarmSettingCrudOperation implements BatchCrudOperation<LongIdKey, AlarmSetting> {

    @Autowired
    private AlarmSettingDao alarmSettingDao;
    @Autowired
    private AlarmHistoryDao alarmHistoryDao;
    @Autowired
    private CurrentAlarmDao currentAlarmDao;
    @Autowired
    private AlarmInfoDao alarmInfoDao;

    @Autowired
    private AlarmSettingCache alarmSettingCache;

    @Autowired
    private EnabledAlarmSettingCache enabledAlarmSettingCache;

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
        LongIdKey pointKey = alarmSetting.getPointKey();
        enabledAlarmSettingCache.delete(pointKey);

        alarmSettingCache.push(alarmSetting, alarmSettingTimeout);
        return alarmSettingDao.insert(alarmSetting);
    }

    @Override
    public void update(AlarmSetting alarmSetting) throws Exception {
        LongIdKey oldPointKey = get(alarmSetting.getKey()).getPointKey();
        enabledAlarmSettingCache.delete(oldPointKey);

        LongIdKey pointKey = alarmSetting.getPointKey();
        enabledAlarmSettingCache.delete(pointKey);

        alarmSettingCache.push(alarmSetting, alarmSettingTimeout);
        alarmSettingDao.update(alarmSetting);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        //清除旧的报警信息的点位相关的使能报警信息缓存。
        LongIdKey oldPointKey = get(key).getPointKey();
        enabledAlarmSettingCache.delete(oldPointKey);

        //如果存在当前报警和报警信息，则删除。
        if (currentAlarmDao.exists(key)) {
            currentAlarmDao.delete(key);
        }
        if (alarmInfoDao.exists(key)) {
            alarmInfoDao.delete(key);
        }

        //找到所有所属的报警历史，将报警信息主键设置为 null。
        List<AlarmHistory> alarmHistories = alarmHistoryDao.lookup(
                AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING, new Object[]{key}
        );
        for (AlarmHistory alarmHistory : alarmHistories) {
            alarmHistory.setAlarmSettingKey(null);
        }
        alarmHistoryDao.batchUpdate(alarmHistories);

        //删除报警设置本身。
        alarmSettingDao.delete(key);
        alarmSettingCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return alarmSettingCache.allExists(keys) || alarmSettingDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return alarmSettingCache.nonExists(keys) && alarmSettingDao.nonExists(keys);
    }

    @Override
    public List<AlarmSetting> batchGet(List<LongIdKey> keys) throws Exception {
        if (alarmSettingCache.allExists(keys)) {
            return alarmSettingCache.batchGet(keys);
        } else {
            if (!alarmSettingDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<AlarmSetting> alarmSettings = alarmSettingDao.batchGet(keys);
            alarmSettingCache.batchPush(alarmSettings, alarmSettingTimeout);
            return alarmSettings;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<AlarmSetting> alarmSettings) throws Exception {
        for (AlarmSetting alarmSetting : alarmSettings) {
            LongIdKey pointKey = alarmSetting.getPointKey();
            enabledAlarmSettingCache.delete(pointKey);
        }

        alarmSettingCache.batchPush(alarmSettings, alarmSettingTimeout);
        return alarmSettingDao.batchInsert(alarmSettings);
    }

    @Override
    public void batchUpdate(List<AlarmSetting> alarmSettings) throws Exception {
        for (AlarmSetting alarmSetting : alarmSettings) {
            LongIdKey oldPointKey = get(alarmSetting.getKey()).getPointKey();
            enabledAlarmSettingCache.delete(oldPointKey);

            LongIdKey pointKey = alarmSetting.getPointKey();
            enabledAlarmSettingCache.delete(pointKey);
        }

        alarmSettingCache.batchPush(alarmSettings, alarmSettingTimeout);
        alarmSettingDao.batchUpdate(alarmSettings);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
