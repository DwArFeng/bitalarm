package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.bitalarm.stack.exception.AlarmDisabledException;
import com.dwarfeng.bitalarm.stack.exception.PointNotExistsException;
import com.dwarfeng.bitalarm.stack.handler.AlarmHandler;
import com.dwarfeng.bitalarm.stack.handler.AlarmLocalCacheHandler;
import com.dwarfeng.bitalarm.stack.handler.ConsumeHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class AlarmHandlerImpl implements AlarmHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmHandlerImpl.class);
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
    private static final int BIT_PER_BYTE = 8;

    @Autowired
    private AlarmInfoMaintainService alarmInfoMaintainService;
    @Autowired
    private CurrentAlarmMaintainService currentAlarmMaintainService;

    @Autowired
    private AlarmLocalCacheHandler alarmLocalCacheHandler;
    @Autowired
    @Qualifier("alarmUpdatedEventConsumeHandler")
    private ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler;
    @Autowired
    @Qualifier("historyRecordedEventConsumeHandler")
    private ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler;
    @Autowired
    @Qualifier("alarmHistoryValueConsumeHandler")
    private ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler;

    // 由于报警逻辑严格与时间相关，此处使用公平锁保证执行顺序的一致性。
    private final Lock lock = new ReentrantLock(true);

    private boolean startFlag = false;

    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() {
        lock.lock();
        try {
            if (!startFlag) {
                LOGGER.info("启用 alarm handler...");
                startFlag = true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {
        lock.lock();
        try {
            if (startFlag) {
                LOGGER.info("禁用 alarm handler...");
                startFlag = false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void processAlarm(long pointId, @SkipRecord byte[] data, Date happenedDate) throws HandlerException {
        lock.lock();
        try {
            // 判断是否允许记录，如果不允许，直接报错。
            if (!isStarted()) {
                throw new AlarmDisabledException();
            }

            // 记录日志，准备工作。
            LOGGER.debug("分析并记录数据点 " + pointId + " 的报警信息: " + toHexString(data));

            // 获取数据点主键。
            LongIdKey pointKey = new LongIdKey(pointId);

            // 获取指定数据点的所有报警设置，同时判断数据点是否存在。
            List<AlarmSetting> alarmSettings = alarmLocalCacheHandler.getAlarmSetting(pointKey);
            if (Objects.isNull(alarmSettings)) {
                throw new PointNotExistsException(pointKey);
            }

            // 分析所有报警设置。
            for (AlarmSetting alarmSetting : alarmSettings) {
                // 判断报警设置的index是否越界，如果越界，则中止。
                int index = alarmSetting.getIndex();
                if (index < 0) {
                    LOGGER.warn("无效的报警配置 " + alarmSetting.getKey() + ": 报警配置的 index 为 " + index + ", 小于0");
                    continue;
                }
                if (index >= data.length * BIT_PER_BYTE) {
                    LOGGER.warn("无效的报警配置 " + alarmSetting.getKey() + ": 报警配置的 index 为 " + index +
                            ", 超过了报警数据的长度 " + data.length * BIT_PER_BYTE);
                    continue;
                }

                // 分析所有报警设置。
                boolean alarming = checkAlarming(data, index);

                // 生成报警记录，记录并推送
                AlarmInfo alarmInfo = new AlarmInfo(alarmSetting.getKey(), pointKey, alarmSetting.getIndex(),
                        alarmSetting.getAlarmMessage(), alarmSetting.getAlarmType(), happenedDate, alarming);
                alarmInfoMaintainService.insertOrUpdate(alarmInfo);
                alarmUpdatedEventConsumeHandler.accept(alarmInfo);

                // 依据是否报警进行操作
                if (alarmInfo.isAlarming()) {
                    processAlarming(alarmInfo);
                } else {
                    processNotAlarming(alarmInfo, pointId);
                }
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    private void processAlarming(AlarmInfo alarmInfo) throws Exception {
        CurrentAlarm currentAlarm = new CurrentAlarm(alarmInfo.getKey(), alarmInfo.getPointKey(), alarmInfo.getIndex(),
                alarmInfo.getAlarmMessage(), alarmInfo.getAlarmType(), alarmInfo.getHappenedDate());
        currentAlarmMaintainService.insertOrUpdate(currentAlarm);
    }

    private void processNotAlarming(AlarmInfo alarmInfo, long pointId) throws Exception {
        CurrentAlarm currentAlarm = currentAlarmMaintainService.getIfExists(alarmInfo.getKey());
        currentAlarmMaintainService.deleteIfExists(alarmInfo.getKey());

        if (!Objects.nonNull(currentAlarm)) {
            return;
        }

        AlarmHistory alarmHistory = new AlarmHistory(null, currentAlarm.getKey(), pointId, currentAlarm.getIndex(),
                currentAlarm.getAlarmMessage(), currentAlarm.getAlarmType(), currentAlarm.getHappenedDate(),
                alarmInfo.getHappenedDate(),
                alarmInfo.getHappenedDate().getTime() - currentAlarm.getHappenedDate().getTime());
        alarmHistoryValueConsumeHandler.accept(alarmHistory);
        historyRecordedEventConsumeHandler.accept(alarmHistory);
    }

    private boolean checkAlarming(byte[] data, int index) {
        // 根据index定位数组的行列。
        int row = index / BIT_PER_BYTE;
        int column = index % BIT_PER_BYTE;
        // 根据row取出具体的某个byte。
        byte b = data[row];
        // 根据column按位运算，返回结果。
        byte mask = (byte) (1 << column);
        return (b & mask) != 0;
    }

    private String toHexString(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
}
