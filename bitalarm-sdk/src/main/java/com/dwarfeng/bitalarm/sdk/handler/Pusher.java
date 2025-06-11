package com.dwarfeng.bitalarm.sdk.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 事件推送器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface Pusher {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 报警更新时执行的推送动作。
     *
     * @param alarmInfo 指定的报警信息。
     * @throws HandlerException 处理器异常。
     */
    void alarmUpdated(AlarmInfo alarmInfo) throws HandlerException;

    /**
     * 报警更新时执行的推送动作。
     *
     * @param alarmInfos 指定的报警信息组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void alarmUpdated(List<AlarmInfo> alarmInfos) throws HandlerException;

    /**
     * 报警历史被记录时的推送动作。
     *
     * @param alarmHistory 指定的报警历史。
     * @throws HandlerException 处理器异常。
     */
    void historyRecorded(AlarmHistory alarmHistory) throws HandlerException;

    /**
     * 报警历史被记录时的推送动作。
     *
     * @param alarmHistories 指定的报警历史组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void historyRecorded(List<AlarmHistory> alarmHistories) throws HandlerException;

    /**
     * 报警功能重置时执行的广播操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void alarmReset() throws HandlerException;
}
