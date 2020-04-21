package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 报警设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class AlarmSetting implements Entity<LongIdKey> {

    private static final long serialVersionUID = -4649627152957048230L;

    private LongIdKey key;
    private long pointId;
    private boolean enabled;
    private int index;
    private String alarmMessage;
    private byte alarmType;
    private String remark;

    public AlarmSetting() {
    }

    public AlarmSetting(
            LongIdKey key, long pointId, boolean enabled, int index, String alarmMessage, byte alarmType,
            String remark) {
        this.key = key;
        this.pointId = pointId;
        this.enabled = enabled;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
        return "AlarmSetting{" +
                "key=" + key +
                ", pointId=" + pointId +
                ", enabled=" + enabled +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", remark='" + remark + '\'' +
                '}';
    }
}
