package com.dwarfeng.bitalarm.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 6000;

    public static final ServiceException.Code ALARM_HANDLER_DISABLED =
            new ServiceException.Code(offset(0), "alarm handler disabled");
    public static final ServiceException.Code CONSUME_HANDLER_STOPPED =
            new ServiceException.Code(offset(10), "consume handler stopped");
    public static final ServiceException.Code POINT_NOT_EXISTS =
            new ServiceException.Code(offset(20), "point not exists");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        ALARM_HANDLER_DISABLED.setCode(offset(0));
        CONSUME_HANDLER_STOPPED.setCode(offset(10));
        POINT_NOT_EXISTS.setCode(offset(20));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
