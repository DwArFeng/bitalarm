package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.key.ByteIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 报警类型指示器维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface AlarmTypeIndicatorMaintainService extends CrudService<ByteIdKey, AlarmTypeIndicator>,
        EntireLookupService<AlarmTypeIndicator> {
}
