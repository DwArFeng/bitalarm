package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.dto.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 事件推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
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
     * 报警出现时执行的推送动作。
     *
     * @param alarmInfo 指定的报警信息。
     * @throws HandlerException 处理器异常。
     */
    void alarmAppeared(AlarmInfo alarmInfo) throws HandlerException;

    /**
     * 报警出现时执行的推送动作。
     *
     * @param alarmInfos 指定的报警信息组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void alarmAppeared(List<AlarmInfo> alarmInfos) throws HandlerException;

    /**
     * 报警消失时执行的推送动作。
     *
     * @param alarmInfo 指定的报警信息。
     * @throws HandlerException 处理器异常。
     */
    void alarmDisappeared(AlarmInfo alarmInfo) throws HandlerException;

    /**
     * 报警消失时执行的推送动作。
     *
     * @param alarmInfos 指定的报警信息组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void alarmDisappeared(List<AlarmInfo> alarmInfos) throws HandlerException;


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
}
