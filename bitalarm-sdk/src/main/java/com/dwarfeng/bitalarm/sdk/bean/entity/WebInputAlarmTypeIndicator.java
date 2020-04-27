package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputByteIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 报警类型指示器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputAlarmTypeIndicator implements Bean {

    private static final long serialVersionUID = 630678365048826333L;

    public static AlarmTypeIndicator toStackBean(WebInputAlarmTypeIndicator webInputAlarmTypeIndicator) {
        if (Objects.isNull(webInputAlarmTypeIndicator)) {
            return null;
        } else {
            return new AlarmTypeIndicator(
                    WebInputByteIdKey.toStackBean(webInputAlarmTypeIndicator.getKey()),
                    webInputAlarmTypeIndicator.getLabel()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputByteIdKey key;

    @JSONField(name = "label")
    @NotEmpty
    private String label;

    public WebInputAlarmTypeIndicator() {
    }

    public WebInputAlarmTypeIndicator(WebInputByteIdKey key, String label) {
        this.key = key;
        this.label = label;
    }

    public WebInputByteIdKey getKey() {
        return key;
    }

    public void setKey(WebInputByteIdKey key) {
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
        return "WebInputAlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                '}';
    }
}
