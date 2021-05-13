package com.dwarfeng.bitalarm.stack.dao;

import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 数据点数据访问层。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public interface PointDao extends BatchBaseDao<LongIdKey, Point>, EntireLookupDao<Point>, PresetLookupDao<Point> {
}
