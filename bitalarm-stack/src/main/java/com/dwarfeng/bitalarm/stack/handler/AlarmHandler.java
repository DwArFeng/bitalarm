package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerStatus;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

import java.util.Date;
import java.util.List;

import static com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerId;

/**
 * 报警处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmHandler extends StartableHandler {

    /**
     * 处理指定的报警信息。
     *
     * @param pointKey     数据点的主键。
     * @param data         详细的报警数据。
     * @param happenedDate 报警的发生时间。
     * @throws HandlerException 处理器异常。
     */
    void processAlarm(LongIdKey pointKey, byte[] data, Date happenedDate) throws HandlerException;

    /**
     * 获取指定的数据点对应的所有报警设置。
     *
     * @param pointKey 指定的数据点主键。
     * @return 指定的数据点对应的所有报警设置。
     * @throws HandlerException 处理器异常。
     */
    List<AlarmSetting> getAlarmSetting(LongIdKey pointKey) throws HandlerException;

    /**
     * 清除本地缓存。
     */
    void clearLocalCache() throws HandlerException;

    /**
     * 获取指定消费者的消费者状态。
     *
     * @param consumerId 指定的消费者ID。
     * @return 消费者状态。
     * @throws HandlerException 处理器异常。
     */
    ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws HandlerException;

    /**
     * 设置指定消费者的参数。
     *
     * @param consumerId  指定的消费者ID。
     * @param bufferSize  缓冲器的大小。
     * @param batchSize   数据的批处理量。
     * @param maxIdleTime 最大空闲时间。
     * @param thread      消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread
    ) throws HandlerException;
}
