package com.dwarfeng.bitalarm.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 记录服务被禁用的异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class ConsumeStoppedException extends HandlerException {

    private static final long serialVersionUID = 1550987463567624638L;

    public ConsumeStoppedException() {
    }

    public ConsumeStoppedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "服务已经被禁用";
    }
}
