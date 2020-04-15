package com.dwarfeng.bitalarm.stack.cache;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 报警历史缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmHistoryCache extends BatchBaseCache<LongIdKey, AlarmHistory> {
}
