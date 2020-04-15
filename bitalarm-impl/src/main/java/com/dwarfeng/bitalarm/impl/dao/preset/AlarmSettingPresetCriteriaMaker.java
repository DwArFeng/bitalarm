package com.dwarfeng.bitalarm.impl.dao.preset;

import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;

@Component
public class AlarmSettingPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
//        switch (s) { TODO
//            case AlarmSettingMaintainService.CHILD_FOR_ALARM_SETTING:
//                childForAlarmSetting(detachedCriteria, objects);
//                break;
//            default:
//                throw new IllegalArgumentException("无法识别的预设: " + s);
//        }
    }
}
