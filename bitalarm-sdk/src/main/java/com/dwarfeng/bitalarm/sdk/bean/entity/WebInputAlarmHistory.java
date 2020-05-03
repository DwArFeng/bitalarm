package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 报警历史。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputAlarmHistory implements Bean {

    private static final long serialVersionUID = -3691357596642270738L;

    public static AlarmHistory toStackBean(WebInputAlarmHistory webInputAlarmHistory) {
        if (Objects.isNull(webInputAlarmHistory)) {
            return null;
        } else {
            return new AlarmHistory(
                    WebInputLongIdKey.toStackBean(webInputAlarmHistory.getKey()),
                    WebInputLongIdKey.toStackBean(webInputAlarmHistory.getAlarmSettingKey()),
                    webInputAlarmHistory.getIndex(),
                    webInputAlarmHistory.getAlarmMessage(),
                    webInputAlarmHistory.getAlarmType(),
                    webInputAlarmHistory.getStartDate(),
                    webInputAlarmHistory.getEndDate(),
                    webInputAlarmHistory.getDuration()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "alarm_setting_key")
    @Valid
    private WebInputLongIdKey alarmSettingKey;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    @JSONField(name = "alarm_message")
    @NotEmpty
    private String alarmMessage;

    @JSONField(name = "alarm_type")
    private byte alarmType;

    @JSONField(name = "start_date")
    @NotNull
    private Date startDate;

    @JSONField(name = "end_date")
    @NotNull
    private Date endDate;

    @JSONField(name = "duration")
    @PositiveOrZero
    private long duration;

    public WebInputAlarmHistory() {
    }

    public WebInputAlarmHistory(
            WebInputLongIdKey key, WebInputLongIdKey alarmSettingKey, int index, String alarmMessage, byte alarmType,
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

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getAlarmSettingKey() {
        return alarmSettingKey;
    }

    public void setAlarmSettingKey(WebInputLongIdKey alarmSettingKey) {
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
