package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 报警信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmInfoMaintainService extends CrudService<LongIdKey, AlarmInfo>,
        EntireLookupService<AlarmInfo> {
}
