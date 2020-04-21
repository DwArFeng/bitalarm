package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Date;

/**
 * 报警服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmService extends Service {

    /**
     * 处理指定的报警信息。
     *
     * @param pointId      数据点的主键。
     * @param data         详细的报警数据。
     * @param happenedDate 报警的发生时间。
     * @throws ServiceException 服务异常。
     */
    void processAlarm(long pointId, byte[] data, Date happenedDate) throws ServiceException;
}
