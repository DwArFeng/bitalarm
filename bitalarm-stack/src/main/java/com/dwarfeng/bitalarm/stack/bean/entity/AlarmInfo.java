package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 报警信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class AlarmInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = 5411174612454808522L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private int index;
    private String alarmMessage;
    private byte alarmType;
    private Date happenedDate;
    private boolean alarming;

    public AlarmInfo() {
    }

    public AlarmInfo(
            LongIdKey key, LongIdKey pointKey, int index, String alarmMessage, byte alarmType, Date happenedDate,
            boolean alarming
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.happenedDate = happenedDate;
        this.alarming = alarming;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
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

    public boolean isAlarming() {
        return alarming;
    }

    public void setAlarming(boolean alarming) {
        this.alarming = alarming;
    }

    @Override
    public String toString() {
        return "AlarmInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                ", alarming=" + alarming +
                '}';
    }
}
