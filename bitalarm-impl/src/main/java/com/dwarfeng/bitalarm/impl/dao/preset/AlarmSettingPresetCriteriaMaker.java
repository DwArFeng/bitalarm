package com.dwarfeng.bitalarm.impl.dao.preset;

import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AlarmSettingPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case AlarmSettingMaintainService.CHILD_FOR_POINT:
                childForPoint(detachedCriteria, objects);
                break;
            case AlarmSettingMaintainService.ENABLED_CHILD_FOR_POINT:
                enabledChildForPoint(detachedCriteria, objects);
                break;
            case AlarmSettingMaintainService.ALARM_MESSAGE_LIKE:
                alarmMessageLike(detachedCriteria, objects);
                break;
            case AlarmSettingMaintainService.ALARM_TYPE_EQUALS:
                alarmTypeEquals(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForPoint(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("pointId"));
            } else {
                long pointId = (long) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("pointId", pointId));
            }
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void enabledChildForPoint(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.eqOrIsNull("enabled", true));
                detachedCriteria.add(Restrictions.isNull("pointId"));
            } else {
                detachedCriteria.add(Restrictions.eqOrIsNull("enabled", true));
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("pointId", longIdKey.getLongId()));
            }
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void alarmMessageLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("alarmMessage", pattern, MatchMode.ANYWHERE));
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void alarmTypeEquals(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            byte alarmType = (byte) objects[0];
            detachedCriteria.add(Restrictions.eq("alarmType", alarmType));
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
