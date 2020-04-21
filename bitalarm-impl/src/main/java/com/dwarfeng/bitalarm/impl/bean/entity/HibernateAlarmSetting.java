package com.dwarfeng.bitalarm.impl.bean.entity;

import com.dwarfeng.bitalarm.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_alarm_setting")
public class HibernateAlarmSetting implements Bean {

    private static final long serialVersionUID = 1846238781655012413L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "point_id")
    private long pointId;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "column_index")
    private int index;

    @Column(name = "alarm_message", length = Constraints.LENGTH_MESSAGE)
    private String alarmMessage;

    @Column(name = "alarm_type")
    private byte alarmType;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateAlarmHistory.class, mappedBy = "alarmSetting")
    private Set<HibernateAlarmHistory> alarmHistories = new HashSet<>();

    public HibernateAlarmSetting() {
    }

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public byte getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(byte alarmType) {
        this.alarmType = alarmType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<HibernateAlarmHistory> getAlarmHistories() {
        return alarmHistories;
    }

    public void setAlarmHistories(Set<HibernateAlarmHistory> alarmHistories) {
        this.alarmHistories = alarmHistories;
    }

    @Override
    public String toString() {
        return "HibernateAlarmSetting{" +
                "longId=" + longId +
                ", pointId=" + pointId +
                ", enabled=" + enabled +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", remark='" + remark + '\'' +
                ", alarmHistories=" + alarmHistories +
                '}';
    }
}
