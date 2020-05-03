package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
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
public class AlarmInfoMaintainServiceImplTest {

    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;
    @Autowired
    private AlarmInfoMaintainService alarmInfoMaintainService;

    private AlarmSetting parentAlarmSetting;
    private AlarmInfo alarmInfo;

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
        alarmInfo = new AlarmInfo(
                null,
                1L,
                1,
                "我是报警信息",
                (byte) 0,
                new Date(),
                true
        );
    }

    @After
    public void tearDown() {
        parentAlarmSetting = null;
        alarmInfo = null;
    }

    @Test
    public void test() throws Exception {
        try {
            parentAlarmSetting.setKey(alarmSettingMaintainService.insert(parentAlarmSetting));
            alarmInfo.setKey(parentAlarmSetting.getKey());
            alarmInfoMaintainService.insert(alarmInfo);
            AlarmInfo alarmInfo1 = alarmInfoMaintainService.get(this.alarmInfo.getKey());
            assertEquals(BeanUtils.describe(alarmInfo), BeanUtils.describe(alarmInfo1));
        } finally {
            alarmInfoMaintainService.deleteIfExists(alarmInfo.getKey());
            alarmSettingMaintainService.deleteIfExists(parentAlarmSetting.getKey());
        }
    }
}
