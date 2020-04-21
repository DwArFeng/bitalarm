package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 报警本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmLocalCacheHandler extends Handler {

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
     *
     * @throws HandlerException 处理器异常。
     */
    void clear() throws HandlerException;
}
