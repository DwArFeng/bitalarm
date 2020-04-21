package com.dwarfeng.bitalarm.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 记录服务被禁用的异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class AlarmDisabledException extends HandlerException {

    private static final long serialVersionUID = 1263442110558485897L;

    public AlarmDisabledException() {
    }

    public AlarmDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlarmDisabledException(String message) {
        super(message);
    }

    public AlarmDisabledException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "报警服务已经被禁用";
    }
}
