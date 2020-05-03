package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 当前报警。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CurrentAlarm implements Entity<LongIdKey> {

    private static final long serialVersionUID = 3699294937879624655L;

    private LongIdKey key;
    private long pointId;
    private int index;
    private String alarmMessage;
    private byte alarmType;
    private Date happenedDate;

    public CurrentAlarm() {
    }

    public CurrentAlarm(
            LongIdKey key, long pointId, int index, String alarmMessage, byte alarmType, Date happenedDate) {
        this.key = key;
        this.pointId = pointId;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.happenedDate = happenedDate;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
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

    public byte getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(byte alarmType) {
        this.alarmType = alarmType;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "CurrentAlarm{" +
                "key=" + key +
                ", pointId=" + pointId +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
