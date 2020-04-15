package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

/**
 * WebInput 当前报警。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputCurrentAlarm implements Bean {

    private static final long serialVersionUID = -267558087245060993L;

    @JSONField(name = "key", ordinal = 1)
    @Valid
    @NotNull
    private FastJsonLongIdKey key;

    @JSONField(name = "index", ordinal = 2)
    @PositiveOrZero
    private int index;

    @JSONField(name = "alarm_message", ordinal = 3)
    @NotEmpty
    private String alarmMessage;

    @JSONField(name = "alarm_type", ordinal = 4)
    private byte alarmType;

    @JSONField(name = "happened_date", ordinal = 5)
    @NotNull
    private Date happenedDate;

    public WebInputCurrentAlarm() {
    }

    public WebInputCurrentAlarm(FastJsonLongIdKey key, int index, String alarmMessage, byte alarmType, Date happenedDate) {
        this.key = key;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "WebInputCurrentAlarm{" +
                "key=" + key +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
