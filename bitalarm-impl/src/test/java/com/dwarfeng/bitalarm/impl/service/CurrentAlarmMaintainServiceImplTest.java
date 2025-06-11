package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService;
import com.dwarfeng.bitalarm.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CurrentAlarmMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;
    @Autowired
    private CurrentAlarmMaintainService currentAlarmMaintainService;

    private Point parentPoint;
    private AlarmSetting parentAlarmSetting;
    private CurrentAlarm currentAlarm;

    @Before
    public void setUp() {
        parentPoint = new Point(new LongIdKey(1), "test-point", "test-point");
        parentAlarmSetting = new AlarmSetting(
                null, parentPoint.getKey(), true, 1, "我是报警信息", "alarmType", "测试用报警设置"
        );
        currentAlarm = new CurrentAlarm(null, parentPoint.getKey(), 1, "我是报警信息", "alarmType", new Date());
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentAlarmSetting = null;
        currentAlarm = null;
    }

    @Test
    public void test() throws Exception {
        try {
            pointMaintainService.insertOrUpdate(parentPoint);
            parentAlarmSetting.setKey(alarmSettingMaintainService.insert(parentAlarmSetting));
            currentAlarm.setKey(parentAlarmSetting.getKey());
            currentAlarmMaintainService.insert(currentAlarm);
            CurrentAlarm currentAlarm1 = currentAlarmMaintainService.get(this.currentAlarm.getKey());
            assertEquals(BeanUtils.describe(this.currentAlarm), BeanUtils.describe(currentAlarm1));
        } finally {
            if (Objects.nonNull(currentAlarm.getKey())) {
                currentAlarmMaintainService.deleteIfExists(currentAlarm.getKey());
            }
            if (Objects.nonNull(parentAlarmSetting.getKey())) {
                alarmSettingMaintainService.deleteIfExists(parentAlarmSetting.getKey());
            }
            if (Objects.nonNull(parentPoint.getKey())) {
                pointMaintainService.deleteIfExists(parentPoint.getKey());
            }
        }
    }
}
