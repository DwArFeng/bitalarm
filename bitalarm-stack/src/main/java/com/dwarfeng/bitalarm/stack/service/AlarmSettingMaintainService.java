package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 报警设置维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmSettingMaintainService extends CrudService<LongIdKey, AlarmSetting>,
        EntireLookupService<AlarmSetting>, PresetLookupService<AlarmSetting> {
}
