package com.dwarfeng.bitalarm.stack.cache;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 报警设置缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmSettingCache extends BatchBaseCache<LongIdKey, AlarmSetting> {
}
