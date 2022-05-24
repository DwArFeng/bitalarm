package com.dwarfeng.bitalarm.stack.cache;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 报警类型指示器缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface AlarmTypeIndicatorCache extends BatchBaseCache<StringIdKey, AlarmTypeIndicator> {
}
