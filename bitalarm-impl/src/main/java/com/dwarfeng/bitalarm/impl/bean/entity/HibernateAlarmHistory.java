package com.dwarfeng.bitalarm.impl.bean.entity;

import com.dwarfeng.bitalarm.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_alarm_history")
public class HibernateAlarmHistory implements Bean {

    private static final long serialVersionUID = -3771822009780091113L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "alarm_setting_id")
    private Long alarmSettingLongId;

    @Column(name = "point_id")
    private Long pointLongId;

    @Column(name = "column_index")
    private int index;

    @Column(name = "alarm_message", length = Constraints.LENGTH_MESSAGE)
    private String alarmMessage;

    @Column(name = "alarm_type")
    private byte alarmType;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "duration")
    private long duration;

    public HibernateAlarmHistory() {
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

    public HibernateLongIdKey getAlarmSettingKey() {
        return Optional.ofNullable(alarmSettingLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setAlarmSettingKey(HibernateLongIdKey key) {
        this.alarmSettingLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getAlarmSettingLongId() {
        return alarmSettingLongId;
    }

    public void setAlarmSettingLongId(Long alarmSettingLongId) {
        this.alarmSettingLongId = alarmSettingLongId;
    }

    public HibernateLongIdKey getPointKey() {
        return Optional.ofNullable(pointLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setPointKey(HibernateLongIdKey key) {
        this.pointLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getPointLongId() {
        return pointLongId;
    }

    public void setPointLongId(Long pointLongId) {
        this.pointLongId = pointLongId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "HibernateAlarmHistory{" +
                "longId=" + longId +
                ", alarmSettingLongId=" + alarmSettingLongId +
                ", pointLongId=" + pointLongId +
                ", index=" + index +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarmType=" + alarmType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                '}';
    }
}
