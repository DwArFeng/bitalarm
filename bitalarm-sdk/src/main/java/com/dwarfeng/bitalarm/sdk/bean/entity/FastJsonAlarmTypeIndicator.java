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

    private static final long serialVersionUID = 8003802017782813925L;

    public static FastJsonAlarmTypeIndicator of(AlarmTypeIndicator alarmTypeIndicator) {
        if (Objects.isNull(alarmTypeIndicator)) {
            return null;
        } else {
            return new FastJsonAlarmTypeIndicator(
                    FastJsonStringIdKey.of(alarmTypeIndicator.getKey()),
                    alarmTypeIndicator.getLabel()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    public FastJsonAlarmTypeIndicator() {
    }

    public FastJsonAlarmTypeIndicator(FastJsonStringIdKey key, String label) {
        this.key = key;
        this.label = label;
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

    @Override
    public String toString() {
        return "FastJsonAlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                '}';
    }
}
