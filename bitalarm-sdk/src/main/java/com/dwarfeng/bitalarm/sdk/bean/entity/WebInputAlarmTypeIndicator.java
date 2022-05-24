package com.dwarfeng.bitalarm.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.bitalarm.sdk.util.Constraints;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

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

    private static final long serialVersionUID = 5809478571833225139L;

    public static AlarmTypeIndicator toStackBean(WebInputAlarmTypeIndicator webInputAlarmTypeIndicator) {
        if (Objects.isNull(webInputAlarmTypeIndicator)) {
            return null;
        } else {
            return new AlarmTypeIndicator(
                    WebInputStringIdKey.toStackBean(webInputAlarmTypeIndicator.getKey()),
                    webInputAlarmTypeIndicator.getLabel(), webInputAlarmTypeIndicator.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "label")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_LABEL)
    private String label;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputAlarmTypeIndicator() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
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
        return "WebInputAlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
