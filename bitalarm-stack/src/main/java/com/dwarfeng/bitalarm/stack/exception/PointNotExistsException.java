package com.dwarfeng.bitalarm.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 数据点不存在异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class PointNotExistsException extends Exception {

    private static final long serialVersionUID = -9207481621936484331L;

    private final LongIdKey pointKey;

    public PointNotExistsException(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    @Override
    public String getMessage() {
        return "数据点不存在: " + pointKey;
    }
}
