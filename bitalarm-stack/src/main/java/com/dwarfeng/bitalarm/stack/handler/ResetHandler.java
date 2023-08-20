package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface ResetHandler extends StartableHandler {

    /**
     * 重置报警功能。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetAlarm() throws HandlerException;
}
