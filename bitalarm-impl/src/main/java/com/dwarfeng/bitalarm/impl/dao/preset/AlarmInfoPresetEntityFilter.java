package com.dwarfeng.bitalarm.impl.dao.preset;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.subgrade.sdk.memory.filter.PresetEntityFilter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AlarmInfoPresetEntityFilter implements PresetEntityFilter<AlarmInfo> {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public boolean accept(AlarmInfo entity, String preset, Object[] objs) {
        switch (preset) {
            case AlarmInfoMaintainService.CHILD_FOR_POINT:
                return childForPoint(entity, objs);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private boolean childForPoint(AlarmInfo entity, Object[] objs) {
        try {
            LongIdKey longIdKey = (LongIdKey) objs[0];
            return Objects.equals(entity.getPointKey(), longIdKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
