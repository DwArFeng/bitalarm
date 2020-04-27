package com.dwarfeng.bitalarm.impl.bean.entity;

import com.dwarfeng.bitalarm.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateByteIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Optional;

@javax.persistence.Entity
@IdClass(HibernateByteIdKey.class)
@Table(name = "tbl_alarm_type_indicator")
public class HibernateAlarmTypeIndicator implements Bean {

    private static final long serialVersionUID = -2632920534789464995L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Byte byteId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "label", length = Constraints.LENGTH_LABEL)
    private String label;

    public HibernateAlarmTypeIndicator() {
    }

    public HibernateByteIdKey getKey() {
        return Optional.ofNullable(byteId).map(HibernateByteIdKey::new).orElse(null);
    }

    public void setKey(HibernateByteIdKey idKey) {
        this.byteId = Optional.ofNullable(idKey).map(HibernateByteIdKey::getByteId).orElse(null);
    }

    public Byte getByteId() {
        return byteId;
    }

    public void setByteId(Byte byteId) {
        this.byteId = byteId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "HibernateTypeIndicator{" +
                "byteId=" + byteId +
                ", label='" + label + '\'' +
                '}';
    }
}
