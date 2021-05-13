package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * WebInput 数据点。
 *
 * @author DwArFeng
 * @since 1.4.4
 */
public class WebInputPoint implements Bean {

    private static final long serialVersionUID = 7255421274266671722L;

    public static Point toStackBean(WebInputPoint webInputPoint) {
        return new Point(
                WebInputLongIdKey.toStackBean(webInputPoint.getKey()),
                webInputPoint.getName(),
                webInputPoint.getRemark()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @JSONField(name = "remark")
    private String remark;

    public WebInputPoint() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
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
        return "WebInputPoint{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
