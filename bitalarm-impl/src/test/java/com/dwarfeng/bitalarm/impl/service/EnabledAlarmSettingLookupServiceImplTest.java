package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.cache.EnabledAlarmSettingCache;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.bitalarm.stack.service.EnabledAlarmSettingLookupService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
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
public class EnabledAlarmSettingLookupServiceImplTest {

    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;
    @Autowired
    private EnabledAlarmSettingLookupService enabledAlarmSettingLookupService;
    @Autowired
    private EnabledAlarmSettingCache enabledAlarmSettingCache;

    private final long pointId = 10086L;
    private final List<AlarmSetting> alarmSettings = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            AlarmSetting alarmSetting = new AlarmSetting(
                    null,
                    pointId,
                    true,
                    i,
                    "我是报警信息",
                    (byte) 0,
                    "测试用报警设置"
            );
            alarmSettings.add(alarmSetting);
        }
        for (int i = 0; i < 5; i++) {
            AlarmSetting alarmSetting = new AlarmSetting(
                    null,
                    pointId,
                    false,
                    i + 100,
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
    public void test() throws ServiceException, CacheException {
        LongIdKey pointIdKey = new LongIdKey(pointId);
        try {
            for (AlarmSetting alarmSetting : alarmSettings) {
                alarmSetting.setKey(alarmSettingMaintainService.insertOrUpdate(alarmSetting));
                alarmSettingMaintainService.update(alarmSetting);
            }
            assertEquals(5, alarmSettingMaintainService.lookup(AlarmSettingMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{pointIdKey}).getCount());
            assertEquals(5, enabledAlarmSettingLookupService.getEnabledAlarmSettings(pointIdKey).size());
            assertEquals(5, enabledAlarmSettingCache.get(pointIdKey).size());
            AlarmSetting alarmSetting = alarmSettings.get(0);
            alarmSettingMaintainService.delete(alarmSetting.getKey());
            assertEquals(0, enabledAlarmSettingCache.get(pointIdKey).size());
            alarmSettingMaintainService.insert(alarmSetting);
            assertEquals(0, enabledAlarmSettingCache.get(pointIdKey).size());
            assertEquals(5, alarmSettingMaintainService.lookup(AlarmSettingMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{pointIdKey}).getCount());
            assertEquals(5, enabledAlarmSettingLookupService.getEnabledAlarmSettings(pointIdKey).size());
            assertEquals(5, enabledAlarmSettingCache.get(pointIdKey).size());
        } finally {
            for (AlarmSetting alarmSetting : alarmSettings) {
                alarmSettingMaintainService.deleteIfExists(alarmSetting.getKey());
            }
        }
    }
}
