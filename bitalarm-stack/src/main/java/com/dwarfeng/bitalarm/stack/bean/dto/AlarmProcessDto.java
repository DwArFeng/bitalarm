package com.dwarfeng.bitalarm.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;

/**
 * 报警处理数据传输对象。
 *
 * @author DwArFeng
 * @since 1.5.1
 */
public class AlarmProcessDto implements Dto {

    private static final long serialVersionUID = -8817526834192046483L;

    private long pointId;
    private byte[] data;
    private Date happenedDate;

    public AlarmProcessDto() {
    }

    public AlarmProcessDto(long pointId, byte[] data, Date happenedDate) {
        this.pointId = pointId;
        this.data = data;
        this.happenedDate = happenedDate;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "AlarmProcessDto{" +
                "pointId=" + pointId +
                ", data=" + Arrays.toString(data) +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
