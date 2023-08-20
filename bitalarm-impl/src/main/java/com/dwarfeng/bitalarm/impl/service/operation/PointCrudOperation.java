package com.dwarfeng.bitalarm.impl.service.operation;

import com.dwarfeng.bitalarm.stack.bean.entity.*;
import com.dwarfeng.bitalarm.stack.cache.AlarmSettingCache;
import com.dwarfeng.bitalarm.stack.cache.EnabledAlarmSettingCache;
import com.dwarfeng.bitalarm.stack.cache.PointCache;
import com.dwarfeng.bitalarm.stack.dao.*;
import com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService;
import com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PointCrudOperation implements BatchCrudOperation<LongIdKey, Point> {

    private final PointDao pointDao;
    private final PointCache pointCache;

    private final AlarmSettingDao alarmSettingDao;
    private final AlarmSettingCache alarmSettingCache;

    private final AlarmInfoDao alarmInfoDao;

    private final CurrentAlarmDao currentAlarmDao;

    private final AlarmHistoryDao alarmHistoryDao;

    private final EnabledAlarmSettingCache enabledAlarmSettingCache;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    public PointCrudOperation(
            PointDao pointDao, PointCache pointCache,
            AlarmSettingDao alarmSettingDao, AlarmSettingCache alarmSettingCache,
            AlarmInfoDao alarmInfoDao,
            CurrentAlarmDao currentAlarmDao,
            AlarmHistoryDao alarmHistoryDao,
            EnabledAlarmSettingCache enabledAlarmSettingCache
    ) {
        this.pointDao = pointDao;
        this.pointCache = pointCache;
        this.alarmSettingDao = alarmSettingDao;
        this.alarmSettingCache = alarmSettingCache;
        this.alarmInfoDao = alarmInfoDao;
        this.currentAlarmDao = currentAlarmDao;
        this.alarmHistoryDao = alarmHistoryDao;
        this.enabledAlarmSettingCache = enabledAlarmSettingCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return pointCache.exists(key) || pointDao.exists(key);
    }

    @Override
    public Point get(LongIdKey key) throws Exception {
        if (pointCache.exists(key)) {
            return pointCache.get(key);
        } else {
            if (!pointDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Point point = pointDao.get(key);
            pointCache.push(point, pointTimeout);
            return point;
        }
    }

    @Override
    public LongIdKey insert(Point point) throws Exception {
        pointCache.push(point, pointTimeout);
        return pointDao.insert(point);
    }

    @Override
    public void update(Point point) throws Exception {
        pointCache.push(point, pointTimeout);
        pointDao.update(point);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        //删除与点位相关的报警设置。
        List<LongIdKey> alarmSettingKeys = alarmSettingDao
                .lookup(AlarmSettingMaintainService.CHILD_FOR_POINT, new Object[]{key})
                .stream().map(AlarmSetting::getKey).collect(Collectors.toList());
        for (LongIdKey alarmSettingKey : alarmSettingKeys) {
            //找到所有所属的报警历史，将报警信息主键设置为 null。
            List<AlarmHistory> alarmHistories = alarmHistoryDao.lookup(
                    AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING, new Object[]{alarmSettingKey}
            );
            for (AlarmHistory alarmHistory : alarmHistories) {
                alarmHistory.setAlarmSettingKey(null);
            }
            alarmHistoryDao.batchUpdate(alarmHistories);
        }
        alarmSettingDao.batchDelete(alarmSettingKeys);
        alarmSettingCache.batchDelete(alarmSettingKeys);

        //删除与点位相关的报警信息。
        List<LongIdKey> alarmInfoKeys = alarmInfoDao
                .lookup(AlarmInfoMaintainService.CHILD_FOR_POINT, new Object[]{key})
                .stream().map(AlarmInfo::getKey).collect(Collectors.toList());
        alarmInfoDao.batchDelete(alarmInfoKeys);

        //删除与点位相关的当前报警。
        List<LongIdKey> currentAlarmKey = currentAlarmDao
                .lookup(CurrentAlarmMaintainService.CHILD_FOR_POINT, new Object[]{key})
                .stream().map(CurrentAlarm::getKey).collect(Collectors.toList());
        currentAlarmDao.batchDelete(currentAlarmKey);

        //使能的数据点持久报警信息缓存信息删除。
        enabledAlarmSettingCache.delete(key);

        //删除数据点自身。
        pointDao.delete(key);
        pointCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return pointCache.allExists(keys) || pointDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return pointCache.nonExists(keys) && pointCache.nonExists(keys);
    }

    @Override
    public List<Point> batchGet(List<LongIdKey> keys) throws Exception {
        if (pointCache.allExists(keys)) {
            return pointCache.batchGet(keys);
        } else {
            if (!pointDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Point> points = pointDao.batchGet(keys);
            pointCache.batchPush(points, pointTimeout);
            return points;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<Point> points) throws Exception {
        pointCache.batchPush(points, pointTimeout);
        return pointDao.batchInsert(points);
    }

    @Override
    public void batchUpdate(List<Point> points) throws Exception {
        pointCache.batchPush(points, pointTimeout);
        pointDao.batchUpdate(points);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
