package com.dwarfeng.bitalarm.impl.bean;

import com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmHistory;
import com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmSetting;
import com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmTypeIndicator;
import com.dwarfeng.bitalarm.impl.bean.entity.HibernatePoint;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
@Mapper
public interface HibernateMapper {

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "alarmSettingLongId", ignore = true)
    HibernateAlarmHistory alarmHistoryToHibernate(AlarmHistory alarmHistory);

    @InheritInverseConfiguration
    AlarmHistory alarmHistoryFromHibernate(HibernateAlarmHistory hibernateAlarmHistory);

    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateAlarmSetting alarmSettingToHibernate(AlarmSetting alarmSetting);

    @InheritInverseConfiguration
    AlarmSetting alarmSettingFromHibernate(HibernateAlarmSetting hibernateAlarmSetting);

    @Mapping(target = "stringId", ignore = true)
    HibernateAlarmTypeIndicator alarmTypeIndicatorToHibernate(AlarmTypeIndicator alarmTypeIndicator);

    @InheritInverseConfiguration
    AlarmTypeIndicator alarmTypeIndicatorFromHibernate(HibernateAlarmTypeIndicator hibernateAlarmTypeIndicator);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "alarmSettings", ignore = true)
    HibernatePoint pointToHibernate(Point point);

    @InheritInverseConfiguration
    Point pointFromHibernate(HibernatePoint hibernatePoint);
}
