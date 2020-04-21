package com.dwarfeng.bitalarm.stack.cache;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.KeyListCache;

/**
 * 使能的数据点持久报警信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface EnabledAlarmSettingCache extends KeyListCache<LongIdKey, AlarmSetting> {
}
