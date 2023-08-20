package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 数据源处理器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface SourceHandler extends Handler {

    /**
     * 列出在用的全部数据源。
     *
     * @return 在用的全部数据源组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<Source> all() throws HandlerException;
}
