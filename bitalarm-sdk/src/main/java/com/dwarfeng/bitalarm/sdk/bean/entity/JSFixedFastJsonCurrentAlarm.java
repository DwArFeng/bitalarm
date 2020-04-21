package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 当前报警。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonCurrentAlarm implements Bean {

    private static final long serialVersionUID = -5496161500703003902L;

    public static JSFixedFastJsonCurrentAlarm of(CurrentAlarm currentAlarm) {
        if (Objects.isNull(currentAlarm)) {
            return null;
        } else {
            return new JSFixedFastJsonCurrentAlarm(
                    JSFixedFastJsonLongIdKey.of(currentAlarm.getKey()),
                    currentAlarm.getIndex(),
                    currentAlarm.getAlarmMessage(),
                    currentAlarm.getAlarmType(),
                    currentAlarm.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "index", ordinal = 2)
    private int index;

    @JSONField(name = "alarm_message", ordinal = 3)
    private String alarmMessage;

    @JSONField(name = "alarm_type", ordinal = 4)
    private byte alarmType;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    public JSFixedFastJsonCurrentAlarm() {
    }

    public JSFixedFastJsonCurrentAlarm(
            JSFixedFastJsonLongIdKey key, int index, String alarmMessage, byte alarmType, Date happenedDate) {
        this.key = key;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.happenedDate = happenedDate;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
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
        return "JSFixedFastJsonCurrentAlarm{" +
                "key=" + key +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
