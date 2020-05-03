package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 报警历史。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonAlarmHistory implements Bean {

    private static final long serialVersionUID = 5568779225046268332L;

    public static JSFixedFastJsonAlarmHistory of(AlarmHistory alarmHistory) {
        if (Objects.isNull(alarmHistory)) {
            return null;
        } else {
            return new JSFixedFastJsonAlarmHistory(
                    JSFixedFastJsonLongIdKey.of(alarmHistory.getKey()),
                    JSFixedFastJsonLongIdKey.of(alarmHistory.getAlarmSettingKey()),
                    alarmHistory.getIndex(),
                    alarmHistory.getAlarmMessage(),
                    alarmHistory.getAlarmType(),
                    alarmHistory.getStartDate(),
                    alarmHistory.getEndDate(),
                    alarmHistory.getDuration()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "alarm_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey alarmSettingKey;

    @JSONField(name = "index", ordinal = 3)
    private int index;

    @JSONField(name = "alarm_message", ordinal = 4)
    private String alarmMessage;

    @JSONField(name = "alarm_type", ordinal = 5)
    private byte alarmType;

    @JSONField(name = "start_date", ordinal = 6)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 7)
    private Date endDate;

    @JSONField(name = "duration", ordinal = 8)
    private long duration;

    public JSFixedFastJsonAlarmHistory() {
    }

    public JSFixedFastJsonAlarmHistory(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey alarmSettingKey, int index, String alarmMessage,
            byte alarmType, Date startDate, Date endDate, long duration) {
        this.key = key;
        this.alarmSettingKey = alarmSettingKey;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getAlarmSettingKey() {
        return alarmSettingKey;
    }

    public void setAlarmSettingKey(JSFixedFastJsonLongIdKey alarmSettingKey) {
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
        return "JSFixedFastJsonAlarmHistory{" +
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
