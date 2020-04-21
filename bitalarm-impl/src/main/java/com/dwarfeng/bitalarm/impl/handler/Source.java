package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 数据源。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Source {

    /**
     * 数据源是否上线。
     *
     * @return 数据源是否上线。
     * @throws HandlerException 处理器异常。
     */
    boolean isOnline() throws HandlerException;

    /**
     * 数据源上线。
     * <p>该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void online() throws HandlerException;

    /**
     * 数据源下线。
     * <p>该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void offline() throws HandlerException;
}
