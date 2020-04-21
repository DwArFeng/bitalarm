package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 报警历史维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmHistoryMaintainService extends BatchCrudService<LongIdKey, AlarmHistory>,
        EntireLookupService<AlarmHistory>, PresetLookupService<AlarmHistory> {

    String CHILD_FOR_ALARM_SETTING = "child_for_alarm_setting";
}
