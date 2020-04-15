package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 当前报警维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CurrentAlarmMaintainService extends CrudService<LongIdKey, CurrentAlarm>,
        EntireLookupService<CurrentAlarm> {
}
