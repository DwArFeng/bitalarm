package com.dwarfeng.bitalarm.sdk.bean;

import com.dwarfeng.bitalarm.sdk.bean.entity.*;
import com.dwarfeng.bitalarm.stack.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    FastJsonAlarmHistory alarmHistoryToFastJson(AlarmHistory alarmHistory);

    @InheritInverseConfiguration
    AlarmHistory alarmHistoryFromFastJson(FastJsonAlarmHistory fastJsonAlarmHistory);

    FastJsonAlarmInfo alarmInfoToFastJson(AlarmInfo alarmInfo);

    @InheritInverseConfiguration
    AlarmInfo alarmInfoFromFastJson(FastJsonAlarmInfo fastJsonAlarmInfo);

    FastJsonAlarmSetting alarmSettingToFastJson(AlarmSetting alarmSetting);

    @InheritInverseConfiguration
    AlarmSetting alarmSettingFromFastJson(FastJsonAlarmSetting fastJsonAlarmSetting);

    FastJsonAlarmTypeIndicator alarmTypeIndicatorToFastJson(AlarmTypeIndicator alarmTypeIndicator);

    @InheritInverseConfiguration
    AlarmTypeIndicator alarmTypeIndicatorFromFastJson(FastJsonAlarmTypeIndicator fastJsonAlarmTypeIndicator);

    FastJsonCurrentAlarm currentAlarmToFastJson(CurrentAlarm currentAlarm);

    @InheritInverseConfiguration
    CurrentAlarm currentAlarmFromFastJson(FastJsonCurrentAlarm fastJsonCurrentAlarm);

    FastJsonPoint pointToFastJson(Point point);

    @InheritInverseConfiguration
    Point pointFromFastJson(FastJsonPoint fastJsonPoint);
}
