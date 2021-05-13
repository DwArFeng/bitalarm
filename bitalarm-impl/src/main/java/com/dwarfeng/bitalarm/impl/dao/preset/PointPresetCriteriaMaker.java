package com.dwarfeng.bitalarm.impl.dao.preset;

import com.dwarfeng.bitalarm.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PointPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        if (PointMaintainService.NAME_LIKE.equals(s)) {
            nameLike(detachedCriteria, objects);
        } else {
            throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void nameLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
