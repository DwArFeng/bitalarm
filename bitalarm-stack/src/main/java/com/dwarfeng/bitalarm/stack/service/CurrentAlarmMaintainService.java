package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 当前报警维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CurrentAlarmMaintainService extends CrudService<LongIdKey, CurrentAlarm>,
        EntireLookupService<CurrentAlarm>, PresetLookupService<CurrentAlarm> {

    String CHILD_FOR_POINT = "child_for_point";

    String CHILD_FOR_POINTS = "child_for_points";
}
