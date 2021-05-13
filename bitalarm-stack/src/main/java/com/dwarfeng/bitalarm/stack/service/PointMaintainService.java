package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 数据点维护服务。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public interface PointMaintainService extends BatchCrudService<LongIdKey, Point>, EntireLookupService<Point>,
        PresetLookupService<Point> {

    String NAME_LIKE = "name_like";
}
