package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson数据点对象。
 *
 * @author DwArFeng
 * @since 1.4.4
 */
public class FastJsonPoint implements Bean {

    private static final long serialVersionUID = -617241637188356512L;

    public static FastJsonPoint of(Point point) {
        if (Objects.isNull(point)) {
            return null;
        }
        return new FastJsonPoint(
                FastJsonLongIdKey.of(point.getKey()),
                point.getName(),
                point.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonPoint() {
    }

    public FastJsonPoint(FastJsonLongIdKey key, String name, String remark) {
        this.key = key;
        this.name = name;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "FastJsonPoint{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
