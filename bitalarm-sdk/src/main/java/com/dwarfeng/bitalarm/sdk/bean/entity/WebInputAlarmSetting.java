package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.Default;
import java.util.Objects;

/**
 * WebInput 报警设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputAlarmSetting implements Bean {

    private static final long serialVersionUID = -8990834706382227655L;

    public static AlarmSetting toStackBean(WebInputAlarmSetting webInputAlarmSetting) {
        if (Objects.isNull(webInputAlarmSetting)) {
            return null;
        } else {
            return new AlarmSetting(
                    WebInputLongIdKey.toStackBean(webInputAlarmSetting.getKey()),
                    webInputAlarmSetting.getPointId(),
                    webInputAlarmSetting.isEnabled(),
                    webInputAlarmSetting.getIndex(),
                    webInputAlarmSetting.getAlarmMessage(),
                    webInputAlarmSetting.getAlarmType(),
                    webInputAlarmSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "point_id")
    private long pointId;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    @JSONField(name = "alarm_message")
    @NotEmpty
    private String alarmMessage;

    @JSONField(name = "alarm_type")
    private byte alarmType;

    @JSONField(name = "remark")
    private String remark;

    public WebInputAlarmSetting() {
    }

    public WebInputAlarmSetting(
            WebInputLongIdKey key, long pointId, boolean enabled, int index, String alarmMessage, byte alarmType,
            String remark) {
        this.key = key;
        this.pointId = pointId;
        this.enabled = enabled;
        this.index = index;
        this.alarmMessage = alarmMessage;
        this.alarmType = alarmType;
        this.remark = remark;
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
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
        return "WebInputAlarmSetting{" +
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
