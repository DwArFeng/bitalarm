package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.Default;
import java.util.Date;

/**
 * WebInput 报警历史。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputAlarmHistory implements Bean {

    private static final long serialVersionUID = -3691357596642270738L;

    @JSONField(name = "key", ordinal = 1)
    @Valid
    @NotNull(groups = Default.class)
    private FastJsonLongIdKey key;

    @JSONField(name = "alarm_setting_key", ordinal = 2)
    @Valid
    private FastJsonLongIdKey alarmSettingKey;

    @JSONField(name = "index", ordinal = 3)
    @PositiveOrZero
    private int index;

    @JSONField(name = "alarm_message", ordinal = 4)
    @NotEmpty
    private String alarmMessage;

    @JSONField(name = "type", ordinal = 5)
    private byte alarmType;

    @JSONField(name = "start_date", ordinal = 6)
    @NotNull
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 7)
    @NotNull
    private Date endDate;

    @JSONField(name = "duration", ordinal = 8)
    @PositiveOrZero
    private long duration;

    public WebInputAlarmHistory() {
    }

    public WebInputAlarmHistory(
            FastJsonLongIdKey key, FastJsonLongIdKey alarmSettingKey, int index, String alarmMessage, byte alarmType,
            Date startDate, Date endDate, Long duration) {
        this.key = key;
        this.alarmSettingKey = alarmSettingKey;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getAlarmSettingKey() {
        return alarmSettingKey;
    }

    public void setAlarmSettingKey(FastJsonLongIdKey alarmSettingKey) {
        this.alarmSettingKey = alarmSettingKey;
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
        return "WebInputAlarmHistory{" +
                "key=" + key +
                ", alarmSettingKey=" + alarmSettingKey +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                '}';
    }
}
