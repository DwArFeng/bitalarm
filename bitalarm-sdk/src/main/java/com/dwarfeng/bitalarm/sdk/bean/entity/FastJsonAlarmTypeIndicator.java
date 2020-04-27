package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonByteIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 报警类型指示器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonAlarmTypeIndicator implements Bean {

    private static final long serialVersionUID = -1267814690336158589L;

    public static FastJsonAlarmTypeIndicator of(AlarmTypeIndicator alarmTypeIndicator) {
        if (Objects.isNull(alarmTypeIndicator)) {
            return null;
        } else {
            return new FastJsonAlarmTypeIndicator(
                    FastJsonByteIdKey.of(alarmTypeIndicator.getKey()),
                    alarmTypeIndicator.getLabel()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonByteIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    public FastJsonAlarmTypeIndicator() {
    }

    public FastJsonAlarmTypeIndicator(FastJsonByteIdKey key, String label) {
        this.key = key;
        this.label = label;
    }

    public FastJsonByteIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonByteIdKey key) {
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
