package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.ByteIdKey;

/**
 * 报警类型指示器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class AlarmTypeIndicator implements Entity<ByteIdKey> {

    private static final long serialVersionUID = 2829705180144769921L;

    private ByteIdKey key;
    private String label;

    public AlarmTypeIndicator() {
    }

    public AlarmTypeIndicator(ByteIdKey key, String label) {
        this.key = key;
        this.label = label;
    }

    @Override
    public ByteIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(ByteIdKey key) {
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
