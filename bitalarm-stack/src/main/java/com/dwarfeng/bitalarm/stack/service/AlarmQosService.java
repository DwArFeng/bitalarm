package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 服务质量服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmQosService extends Service {

    /**
     * 清除本地缓存。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 开始处理报警信息。
     *
     * @throws ServiceException 服务异常。
     */
    void startAlarm() throws ServiceException;

    /**
     * 停止处理报警信息。
     *
     * @throws ServiceException 服务异常。
     */
    void stopAlarm() throws ServiceException;
}
