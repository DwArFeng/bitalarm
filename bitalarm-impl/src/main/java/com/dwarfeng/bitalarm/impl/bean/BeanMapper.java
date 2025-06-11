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
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>impl</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Mapper
public interface BeanMapper {
    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    // -----------------------------------------------------------Bitalarm Key-----------------------------------------------------------
    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "alarmSettingLongId", ignore = true)
    HibernateAlarmHistory alarmHistoryToHibernate(AlarmHistory alarmHistory);

    @InheritInverseConfiguration
    AlarmHistory alarmHistoryFromHibernate(HibernateAlarmHistory hibernateAlarmHistory);

    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    HibernateAlarmSetting alarmSettingToHibernate(AlarmSetting alarmSetting);

    @InheritInverseConfiguration
    AlarmSetting alarmSettingFromHibernate(HibernateAlarmSetting hibernateAlarmSetting);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    HibernateAlarmTypeIndicator alarmTypeIndicatorToHibernate(AlarmTypeIndicator alarmTypeIndicator);

    @InheritInverseConfiguration
    AlarmTypeIndicator alarmTypeIndicatorFromHibernate(HibernateAlarmTypeIndicator hibernateAlarmTypeIndicator);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "alarmSettings", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    HibernatePoint pointToHibernate(Point point);

    @InheritInverseConfiguration
    Point pointFromHibernate(HibernatePoint hibernatePoint);
}
