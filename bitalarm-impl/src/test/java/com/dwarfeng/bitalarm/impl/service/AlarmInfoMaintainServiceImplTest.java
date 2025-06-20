package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
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
public class AlarmInfoMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;
    @Autowired
    private AlarmInfoMaintainService alarmInfoMaintainService;

    private Point parentPoint;
    private AlarmSetting parentAlarmSetting;
    private AlarmInfo alarmInfo;

    @Before
    public void setUp() {
        parentPoint = new Point(new LongIdKey(1), "test-point", "test-point");
        parentAlarmSetting = new AlarmSetting(
                null, parentPoint.getKey(), true, 1, "我是报警信息", "alarmType", "测试用报警设置"
        );
        alarmInfo = new AlarmInfo(null, parentPoint.getKey(), 1, "我是报警信息", "alarmType", new Date(), true);
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentAlarmSetting = null;
        alarmInfo = null;
    }

    @Test
    public void test() throws Exception {
        try {
            pointMaintainService.insertOrUpdate(parentPoint);
            parentAlarmSetting.setKey(alarmSettingMaintainService.insert(parentAlarmSetting));
            alarmInfo.setKey(parentAlarmSetting.getKey());
            alarmInfoMaintainService.insert(alarmInfo);
            AlarmInfo alarmInfo1 = alarmInfoMaintainService.get(this.alarmInfo.getKey());
            assertEquals(BeanUtils.describe(alarmInfo), BeanUtils.describe(alarmInfo1));
        } finally {
            if (Objects.nonNull(alarmInfo.getKey())) {
                alarmInfoMaintainService.deleteIfExists(alarmInfo.getKey());
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
