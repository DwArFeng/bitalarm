package com.dwarfeng.bitalarm.sdk.bean;

import com.dwarfeng.bitalarm.sdk.bean.entity.*;
import com.dwarfeng.bitalarm.stack.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // -----------------------------------------------------------Bitalarm Key-----------------------------------------------------------
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

    JSFixedFastJsonAlarmHistory alarmHistoryToJSFixedFastJson(AlarmHistory alarmHistory);

    @InheritInverseConfiguration
    AlarmHistory alarmHistoryFromJSFixedFastJson(JSFixedFastJsonAlarmHistory jSFixedFastJsonAlarmHistory);

    JSFixedFastJsonAlarmInfo alarmInfoToJSFixedFastJson(AlarmInfo alarmInfo);

    @InheritInverseConfiguration
    AlarmInfo alarmInfoFromJSFixedFastJson(JSFixedFastJsonAlarmInfo jSFixedFastJsonAlarmInfo);

    JSFixedFastJsonAlarmSetting alarmSettingToJSFixedFastJson(AlarmSetting alarmSetting);

    @InheritInverseConfiguration
    AlarmSetting alarmSettingFromJSFixedFastJson(JSFixedFastJsonAlarmSetting jSFixedFastJsonAlarmSetting);

    JSFixedFastJsonCurrentAlarm currentAlarmToJSFixedFastJson(CurrentAlarm currentAlarm);

    @InheritInverseConfiguration
    CurrentAlarm currentAlarmFromJSFixedFastJson(JSFixedFastJsonCurrentAlarm jSFixedFastJsonCurrentAlarm);

    JSFixedFastJsonPoint pointToJSFixedFastJson(Point point);

    @InheritInverseConfiguration
    Point pointFromJSFixedFastJson(JSFixedFastJsonPoint jSFixedFastJsonPoint);

    WebInputAlarmHistory alarmHistoryToWebInput(AlarmHistory alarmHistory);

    @InheritInverseConfiguration
    AlarmHistory alarmHistoryFromWebInput(WebInputAlarmHistory webInputAlarmHistory);

    WebInputAlarmInfo alarmInfoToWebInput(AlarmInfo alarmInfo);

    @InheritInverseConfiguration
    AlarmInfo alarmInfoFromWebInput(WebInputAlarmInfo webInputAlarmInfo);

    WebInputAlarmSetting alarmSettingToWebInput(AlarmSetting alarmSetting);

    @InheritInverseConfiguration
    AlarmSetting alarmSettingFromWebInput(WebInputAlarmSetting webInputAlarmSetting);

    WebInputAlarmTypeIndicator alarmTypeIndicatorToWebInput(AlarmTypeIndicator alarmTypeIndicator);

    @InheritInverseConfiguration
    AlarmTypeIndicator alarmTypeIndicatorFromWebInput(WebInputAlarmTypeIndicator webInputAlarmTypeIndicator);

    WebInputCurrentAlarm currentAlarmToWebInput(CurrentAlarm currentAlarm);

    @InheritInverseConfiguration
    CurrentAlarm currentAlarmFromWebInput(WebInputCurrentAlarm webInputCurrentAlarm);

    WebInputPoint pointToWebInput(Point point);

    @InheritInverseConfiguration
    Point pointFromWebInput(WebInputPoint webInputPoint);
}
