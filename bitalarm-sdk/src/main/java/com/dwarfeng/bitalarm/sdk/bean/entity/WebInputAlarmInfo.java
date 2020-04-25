package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 报警信息。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public class WebInputAlarmInfo implements Bean {

    private static final long serialVersionUID = 7296921847414242775L;

    public static AlarmInfo toStackBean(WebInputAlarmInfo webInputAlarmInfo) {
        if (Objects.isNull(webInputAlarmInfo)) {
            return null;
        } else {
            return new AlarmInfo(
                    WebInputLongIdKey.toStackBean(webInputAlarmInfo.getKey()),
                    webInputAlarmInfo.getIndex(),
                    webInputAlarmInfo.getAlarmMessage(),
                    webInputAlarmInfo.getAlarmType(),
                    webInputAlarmInfo.getHappenedDate(),
                    webInputAlarmInfo.isAlarming()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputLongIdKey key;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    @JSONField(name = "alarm_message")
    @NotEmpty
    private String alarmMessage;

    @JSONField(name = "alarm_type")
    private byte alarmType;

    @JSONField(name = "happened_date")
    @NotNull
    private Date happenedDate;

    @JSONField(name = "alarming")
    private boolean alarming;

    public WebInputAlarmInfo() {
    }

    public WebInputAlarmInfo(
            WebInputLongIdKey key, int index, String alarmMessage, byte alarmType, Date happenedDate,
            boolean alarming) {
        this.key = key;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.happenedDate = happenedDate;
        this.alarming = alarming;
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
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

    public boolean isAlarming() {
        return alarming;
    }

    public void setAlarming(boolean alarming) {
        this.alarming = alarming;
    }

    @Override
    public String toString() {
        return "WebInputAlarmInfo{" +
                "key=" + key +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                ", alarming=" + alarming +
                '}';
    }
}
