package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 报警信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonAlarmInfo implements Bean {

    private static final long serialVersionUID = -7816620407316562535L;

    public static JSFixedFastJsonAlarmInfo of(AlarmInfo alarmInfo) {
        if (Objects.isNull(alarmInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonAlarmInfo(
                    JSFixedFastJsonLongIdKey.of(alarmInfo.getKey()),
                    alarmInfo.getPointId(),
                    alarmInfo.getIndex(),
                    alarmInfo.getAlarmMessage(),
                    alarmInfo.getAlarmType(),
                    alarmInfo.getHappenedDate(),
                    alarmInfo.isAlarming()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_id", ordinal = 2, serializeUsing = ToStringSerializer.class)
    private long pointId;

    @JSONField(name = "index", ordinal = 3)
    private int index;

    @JSONField(name = "alarm_message", ordinal = 4)
    private String alarmMessage;

    @JSONField(name = "alarm_type", ordinal = 5)
    private byte alarmType;

    @JSONField(name = "happened_date", ordinal = 6)
    private Date happenedDate;

    @JSONField(name = "alarming", ordinal = 7)
    private boolean alarming;

    public JSFixedFastJsonAlarmInfo() {
    }

    public JSFixedFastJsonAlarmInfo(
            JSFixedFastJsonLongIdKey key, long pointId, int index, String alarmMessage, byte alarmType,
            Date happenedDate, boolean alarming) {
        this.key = key;
        this.pointId = pointId;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.happenedDate = happenedDate;
        this.alarming = alarming;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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
}
