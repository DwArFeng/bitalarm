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

    private static final long serialVersionUID = 7301955764558170245L;

    private StringIdKey key;
    private String label;
    private String remark;

    public AlarmTypeIndicator() {
    }

    public AlarmTypeIndicator(StringIdKey key, String label, String remark) {
        this.key = key;
        this.label = label;
        this.remark = remark;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
