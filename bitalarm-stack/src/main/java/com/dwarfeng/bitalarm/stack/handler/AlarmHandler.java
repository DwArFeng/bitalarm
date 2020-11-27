package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.Date;

/**
 * 报警处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmHandler extends Handler {

    /**
     * 报警处理器是否启动。
     *
     * @return 报警处理器是否启动。
     * @throws HandlerException 处理器异常。
     */
    boolean isStarted() throws HandlerException;

    /**
     * 开启报警处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 关闭报警处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 处理指定的报警信息。
     *
     * @param pointId      数据点的主键。
     * @param data         详细的报警数据。
     * @param happenedDate 报警的发生时间。
     * @throws HandlerException 处理器异常。
     */
    void processAlarm(long pointId, byte[] data, Date happenedDate) throws HandlerException;
}
