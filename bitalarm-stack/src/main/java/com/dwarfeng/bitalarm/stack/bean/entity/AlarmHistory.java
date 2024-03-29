package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 报警历史。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class AlarmHistory implements Entity<LongIdKey> {

    private static final long serialVersionUID = -5682829213831120832L;

    private LongIdKey key;
    private LongIdKey alarmSettingKey;
    private LongIdKey pointKey;
    private int index;
    private String alarmMessage;
    private String alarmType;
    private Date startDate;
    private Date endDate;
    private long duration;

    public AlarmHistory() {
    }

    public AlarmHistory(
            LongIdKey key, LongIdKey alarmSettingKey, LongIdKey pointKey, int index, String alarmMessage, String alarmType,
            Date startDate, Date endDate, long duration) {
        this.key = key;
        this.alarmSettingKey = alarmSettingKey;
        this.pointKey = pointKey;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getAlarmSettingKey() {
        return alarmSettingKey;
    }

    public void setAlarmSettingKey(LongIdKey alarmSettingKey) {
        this.alarmSettingKey = alarmSettingKey;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "AlarmHistory{" +
                "key=" + key +
                ", alarmSettingKey=" + alarmSettingKey +
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                '}';
    }
}
