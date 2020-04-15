package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.Default;

/**
 * WebInput 报警设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputAlarmSetting implements Bean {

    private static final long serialVersionUID = -8990834706382227655L;

    @JSONField(name = "key", ordinal = 1)
    @Valid
    @NotNull(groups = Default.class)
    private FastJsonLongIdKey key;

    @JSONField(name = "point_id", ordinal = 2)
    private long pointId;

    @JSONField(name = "index", ordinal = 3)
    @PositiveOrZero
    private int index;

    @JSONField(name = "alarm_message", ordinal = 4)
    @NotEmpty
    private String alarmMessage;

    @JSONField(name = "alarm_type", ordinal = 5)
    private byte alarmType;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public WebInputAlarmSetting() {
    }

    public WebInputAlarmSetting(
            FastJsonLongIdKey key, long pointId, int index, String alarmMessage, byte alarmType, String remark) {
        this.key = key;
        this.pointId = pointId;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputAlarmSetting{" +
                "key=" + key +
                ", pointId=" + pointId +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", remark='" + remark + '\'' +
                '}';
    }
}
