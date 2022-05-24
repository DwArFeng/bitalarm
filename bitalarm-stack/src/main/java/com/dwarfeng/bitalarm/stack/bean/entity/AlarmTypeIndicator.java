package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 报警类型指示器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class AlarmTypeIndicator implements Entity<StringIdKey> {

    private static final long serialVersionUID = -6629140910520378707L;

    private StringIdKey key;
    private String label;

    public AlarmTypeIndicator() {
    }

    public AlarmTypeIndicator(StringIdKey key, String label) {
        this.key = key;
        this.label = label;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
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
        return "AlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                '}';
    }
}
