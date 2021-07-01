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
    String ALARM_MESSAGE_LIKE = "alarm_message_like";
    String ALARM_TYPE_EQUALS = "alarm_type_equals";
    String START_DATE_BETWEEN = "start_date_between";
    String END_DATE_BETWEEN = "end_date_between";
    String CHILD_FOR_ALARM_SETTING_START_DATE_BETWEEN = "child_for_alarm_setting_start_date_between";
    String CHILD_FOR_ALARM_SETTING_END_DATE_BETWEEN = "child_for_alarm_setting_end_date_between";
    String DURATION_GT = "duration_gt";
    String DURATION_LT = "duration_lt";
    String CHILD_FOR_ALARM_SETTING_DURATION_GT = "child_for_alarm_setting_duration_gt";
    String CHILD_FOR_ALARM_SETTING_DURATION_LT = "child_for_alarm_setting_duration_lt";
}
