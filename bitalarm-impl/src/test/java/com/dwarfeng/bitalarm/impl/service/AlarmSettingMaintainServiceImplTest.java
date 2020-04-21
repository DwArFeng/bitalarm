package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AlarmSettingMaintainServiceImplTest {

    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;

    private List<AlarmSetting> alarmSettings;

    @Before
    public void setUp() {
        alarmSettings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AlarmSetting alarmSetting = new AlarmSetting(
                    null,
                    1L,
                    true,
                    1,
                    "我是报警信息",
                    (byte) 0,
                    "测试用报警设置"
            );
            alarmSettings.add(alarmSetting);
        }
    }

    @After
    public void tearDown() {
        alarmSettings.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (AlarmSetting alarmSetting : alarmSettings) {
                alarmSetting.setKey(alarmSettingMaintainService.insert(alarmSetting));
                alarmSettingMaintainService.update(alarmSetting);
                AlarmSetting testAlarmSetting = alarmSettingMaintainService.get(alarmSetting.getKey());
                assertEquals(BeanUtils.describe(alarmSetting), BeanUtils.describe(testAlarmSetting));
            }
        } finally {
            for (AlarmSetting alarmSetting : alarmSettings) {
                alarmSettingMaintainService.deleteIfExists(alarmSetting.getKey());
            }
        }
    }
}
