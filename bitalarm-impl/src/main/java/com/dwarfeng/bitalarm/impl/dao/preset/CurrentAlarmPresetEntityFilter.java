package com.dwarfeng.bitalarm.impl.dao.preset;

import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService;
import com.dwarfeng.subgrade.sdk.memory.filter.PresetEntityFilter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class CurrentAlarmPresetEntityFilter implements PresetEntityFilter<CurrentAlarm> {

    @Override
    public boolean accept(CurrentAlarm entity, String preset, Object[] objs) {
        switch (preset) {
            case CurrentAlarmMaintainService.CHILD_FOR_POINT:
                return childForPoint(entity, objs);
            case CurrentAlarmMaintainService.CHILD_FOR_POINTS:
                return childForPoints(entity, objs);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private boolean childForPoint(CurrentAlarm entity, Object[] objs) {
        try {
            LongIdKey longIdKey = (LongIdKey) objs[0];
            return Objects.equals(entity.getPointKey(), longIdKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private boolean childForPoints(CurrentAlarm entity, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                return false;
            }

            List<LongIdKey> keys = (List<LongIdKey>) objs[0];
            return keys.contains(entity.getPointKey());
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
