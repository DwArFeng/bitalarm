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

    private static final long serialVersionUID = 9213124964290194936L;

    public static JSFixedFastJsonCurrentAlarm of(CurrentAlarm currentAlarm) {
        if (Objects.isNull(currentAlarm)) {
            return null;
        } else {
            return new JSFixedFastJsonCurrentAlarm(
                    JSFixedFastJsonLongIdKey.of(currentAlarm.getKey()),
                    JSFixedFastJsonLongIdKey.of(currentAlarm.getPointKey()),
                    currentAlarm.getIndex(),
                    currentAlarm.getAlarmMessage(),
                    currentAlarm.getAlarmType(),
                    currentAlarm.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "index", ordinal = 3)
    private int index;

    @JSONField(name = "alarm_message", ordinal = 4)
    private String alarmMessage;

    @JSONField(name = "alarm_type", ordinal = 5)
    private String alarmType;

    @JSONField(name = "happened_date", ordinal = 6)
    private Date happenedDate;

    public JSFixedFastJsonCurrentAlarm() {
    }

    public JSFixedFastJsonCurrentAlarm(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey pointKey, int index, String alarmMessage,
            String alarmType, Date happenedDate
    ) {
        this.key = key;
        this.pointKey = pointKey;
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

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
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
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
