package com.dwarfeng.bitalarm.impl.dao.preset;

import com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class AlarmHistoryPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING:
                childForAlarmSetting(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.ALARM_MESSAGE_LIKE:
                alarmMessageLike(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.ALARM_TYPE_EQUALS:
                alarmTypeEquals(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.START_DATE_BETWEEN:
                startDateBetween(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.END_DATE_BETWEEN:
                endDateBetween(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING_START_DATE_BETWEEN:
                childForAlarmSettingStartDateBetween(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING_END_DATE_BETWEEN:
                childForAlarmSettingEndDateBetween(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.DURATION_GT:
                durationGt(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.DURATION_LT:
                durationLt(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING_DURATION_GT:
                childForAlarmSettingDurationGt(detachedCriteria, objects);
                break;
            case AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING_DURATION_LT:
                childForAlarmSettingDurationLt(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForAlarmSetting(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("alarmSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("alarmSettingLongId", longIdKey.getLongId()));
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

    private void startDateBetween(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Date startDate = (Date) objects[0];
            Date endDate = (Date) objects[1];
            detachedCriteria.add(Restrictions.ge("startDate", startDate));
            detachedCriteria.add(Restrictions.lt("startDate", endDate));
            detachedCriteria.addOrder(Order.asc("startDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void endDateBetween(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Date startDate = (Date) objects[0];
            Date endDate = (Date) objects[1];
            detachedCriteria.add(Restrictions.ge("endDate", startDate));
            detachedCriteria.add(Restrictions.lt("endDate", endDate));
            detachedCriteria.addOrder(Order.asc("endDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForAlarmSettingStartDateBetween(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("alarmSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("alarmSettingLongId",
                        longIdKey.getLongId()));
            }
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];
            detachedCriteria.add(Restrictions.ge("startDate", startDate));
            detachedCriteria.add(Restrictions.lt("startDate", endDate));
            detachedCriteria.addOrder(Order.asc("startDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForAlarmSettingEndDateBetween(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("alarmSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("alarmSettingLongId",
                        longIdKey.getLongId()));
            }
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];
            detachedCriteria.add(Restrictions.ge("endDate", startDate));
            detachedCriteria.add(Restrictions.lt("endDate", endDate));
            detachedCriteria.addOrder(Order.asc("endDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void durationGt(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            long duration = (long) objects[0];
            detachedCriteria.add(Restrictions.gt("duration", duration));
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void durationLt(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            long duration = (long) objects[0];
            detachedCriteria.add(Restrictions.lt("duration", duration));
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForAlarmSettingDurationGt(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("alarmSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("alarmSettingLongId", longIdKey.getLongId()));
            }
            long duration = (long) objects[1];
            detachedCriteria.add(Restrictions.gt("duration", duration));
            detachedCriteria.addOrder(Order.asc("longId"));
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForAlarmSettingDurationLt(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("alarmSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("alarmSettingLongId", longIdKey.getLongId()));
            }
            long duration = (long) objects[1];
            detachedCriteria.add(Restrictions.lt("duration", duration));
            detachedCriteria.addOrder(Order.asc("longId"));
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
