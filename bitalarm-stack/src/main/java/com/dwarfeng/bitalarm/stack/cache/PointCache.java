package com.dwarfeng.bitalarm.stack.cache;

import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 数据点缓存。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public interface PointCache extends BatchBaseCache<LongIdKey, Point> {
}
