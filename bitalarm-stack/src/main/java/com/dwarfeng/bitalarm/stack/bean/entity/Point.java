package com.dwarfeng.bitalarm.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 数据点。
 *
 * @author DwArFeng
 * @since 1.4.4
 */
public class Point implements Entity<LongIdKey> {

    private static final long serialVersionUID = -3064691865635456107L;

    /**
     * 主键。
     */
    private LongIdKey key;
    /**
     * 数据点的名称。
     */
    private String name;
    /**
     * 备注。
     */
    private String remark;

    public Point() {
    }

    public Point(LongIdKey key, String name, String remark) {
        this.key = key;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Point{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
