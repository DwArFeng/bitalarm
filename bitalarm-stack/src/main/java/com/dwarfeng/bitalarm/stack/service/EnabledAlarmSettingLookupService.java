package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 有效的报警设置查询服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface EnabledAlarmSettingLookupService extends Service {

    /**
     * 获取指定的数据点所属的有效的过滤器信息。
     *
     * @param pointKey 指定的数据点。
     * @return 指定的数据点所属的有效的过滤器信息。
     * @throws ServiceException 服务异常。
     */
    List<AlarmSetting> getEnabledAlarmSettings(LongIdKey pointKey) throws ServiceException;
}
