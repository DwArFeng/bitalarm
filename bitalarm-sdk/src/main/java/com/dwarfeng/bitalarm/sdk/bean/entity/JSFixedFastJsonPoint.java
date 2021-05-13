package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 数据点。
 *
 * @author DwArFeng
 * @since 1.4.4
 */
public class JSFixedFastJsonPoint implements Bean {

    private static final long serialVersionUID = 3886401281110964131L;

    public static JSFixedFastJsonPoint of(Point point) {
        if (Objects.isNull(point)) {
            return null;
        }
        return new JSFixedFastJsonPoint(
                JSFixedFastJsonLongIdKey.of(point.getKey()),
                point.getName(),
                point.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public JSFixedFastJsonPoint() {
    }

    public JSFixedFastJsonPoint(JSFixedFastJsonLongIdKey key, String name, String remark) {
        this.key = key;
        this.name = name;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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
        return "JSFixedFastJsonPoint{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
