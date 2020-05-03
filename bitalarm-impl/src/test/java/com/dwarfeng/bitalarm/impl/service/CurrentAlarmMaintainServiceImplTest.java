package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CurrentAlarmMaintainServiceImplTest {

    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;
    @Autowired
    private CurrentAlarmMaintainService currentAlarmMaintainService;

    private AlarmSetting parentAlarmSetting;
    private CurrentAlarm currentAlarm;

    @Before
    public void setUp() {
        parentAlarmSetting = new AlarmSetting(
                null,
                1L,
                true,
                1,
                "我是报警信息",
                (byte) 0,
                "测试用报警设置"
        );
        currentAlarm = new CurrentAlarm(
                null,
                1L,
                1,
                "我是报警信息",
                (byte) 0,
                new Date()
        );
    }

    @After
    public void tearDown() {
        parentAlarmSetting = null;
        currentAlarm = null;
    }

    @Test
    public void test() throws Exception {
        try {
            parentAlarmSetting.setKey(alarmSettingMaintainService.insert(parentAlarmSetting));
            currentAlarm.setKey(parentAlarmSetting.getKey());
            currentAlarmMaintainService.insert(currentAlarm);
            CurrentAlarm currentAlarm1 = currentAlarmMaintainService.get(this.currentAlarm.getKey());
            assertEquals(BeanUtils.describe(this.currentAlarm), BeanUtils.describe(currentAlarm1));
        } finally {
            currentAlarmMaintainService.deleteIfExists(currentAlarm.getKey());
            alarmSettingMaintainService.deleteIfExists(parentAlarmSetting.getKey());
        }
    }
}
