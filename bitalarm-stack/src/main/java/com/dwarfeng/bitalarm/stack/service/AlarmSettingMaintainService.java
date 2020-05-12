package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 报警设置维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmSettingMaintainService extends BatchCrudService<LongIdKey, AlarmSetting>,
        EntireLookupService<AlarmSetting>, PresetLookupService<AlarmSetting> {

    String CHILD_FOR_POINT = "child_for_point";
    String ENABLED_CHILD_FOR_POINT = "enabled_child_for_point";
    String ALARM_MESSAGE_LIKE = "alarm_message_like";
    String ALARM_TYPE_EQUALS = "alarm_type_equals";
}
