package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 当前报警。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputCurrentAlarm implements Bean {

    private static final long serialVersionUID = -6067974433544546135L;

    public static CurrentAlarm toStackBean(WebInputCurrentAlarm webInputCurrentAlarm) {
        if (Objects.isNull(webInputCurrentAlarm)) {
            return null;
        } else {
            return new CurrentAlarm(
                    WebInputLongIdKey.toStackBean(webInputCurrentAlarm.getKey()),
                    WebInputLongIdKey.toStackBean(webInputCurrentAlarm.getPointKey()),
                    webInputCurrentAlarm.getIndex(),
                    webInputCurrentAlarm.getAlarmMessage(),
                    webInputCurrentAlarm.getAlarmType(),
                    webInputCurrentAlarm.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputLongIdKey key;

    @JSONField(name = "point_key")
    @Valid
    private WebInputLongIdKey pointKey;

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

    public WebInputCurrentAlarm() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(WebInputLongIdKey pointKey) {
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

    @Override
    public String toString() {
        return "WebInputCurrentAlarm{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
