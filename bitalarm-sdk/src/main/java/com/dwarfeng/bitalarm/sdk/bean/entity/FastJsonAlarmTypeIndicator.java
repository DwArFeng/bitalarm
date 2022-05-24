package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 报警类型指示器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonAlarmTypeIndicator implements Bean {

    private static final long serialVersionUID = 8205812981741271080L;

    public static FastJsonAlarmTypeIndicator of(AlarmTypeIndicator alarmTypeIndicator) {
        if (Objects.isNull(alarmTypeIndicator)) {
            return null;
        } else {
            return new FastJsonAlarmTypeIndicator(
                    FastJsonStringIdKey.of(alarmTypeIndicator.getKey()),
                    alarmTypeIndicator.getLabel(), alarmTypeIndicator.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonAlarmTypeIndicator() {
    }

    public FastJsonAlarmTypeIndicator(FastJsonStringIdKey key, String label, String remark) {
        this.key = key;
        this.label = label;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonAlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
