package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService;
import com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.bitalarm.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AlarmHistoryMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;
    @Autowired
    private AlarmHistoryMaintainService alarmHistoryMaintainService;

    private Point parentPoint;
    private AlarmSetting parentAlarmSetting;
    private List<AlarmHistory> alarmHistories;

    @Before
    public void setUp() {
        parentPoint = new Point(
                new LongIdKey(1),
                "test-point",
                "test-point"
        );
        Date date = new Date();
        parentAlarmSetting = new AlarmSetting(
                null,
                parentPoint.getKey(),
                true,
                1,
                "我是报警信息",
                "alarmType",
                "测试用报警设置"
        );
        alarmHistories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AlarmHistory alarmHistory = new AlarmHistory(
                    null,
                    null,
                    null,
                    1,
                    "我是报警信息",
                    "alarmType",
                    date,
                    date,
                    0L
            );
            alarmHistories.add(alarmHistory);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentAlarmSetting = null;
        alarmHistories.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            pointMaintainService.insertOrUpdate(parentPoint);
            parentAlarmSetting.setKey(alarmSettingMaintainService.insert(parentAlarmSetting));
            for (AlarmHistory alarmHistory : alarmHistories) {
                alarmHistory.setKey(alarmHistoryMaintainService.insert(alarmHistory));
                alarmHistory.setAlarmSettingKey(parentAlarmSetting.getKey());
                alarmHistoryMaintainService.update(alarmHistory);
                AlarmHistory testAlarmHistory = alarmHistoryMaintainService.get(alarmHistory.getKey());
                // 由于 alarmHistory 无缓存，因此处理与日期有关的字段，保证 toString 一致。
                testAlarmHistory.setStartDate(new Date(testAlarmHistory.getStartDate().getTime()));
                testAlarmHistory.setEndDate(new Date(testAlarmHistory.getEndDate().getTime()));
                assertEquals(BeanUtils.describe(alarmHistory), BeanUtils.describe(testAlarmHistory));
            }
            PagedData<AlarmHistory> lookup = alarmHistoryMaintainService.lookup(
                    AlarmHistoryMaintainService.CHILD_FOR_ALARM_SETTING, new Object[]{parentAlarmSetting.getKey()});
            assertEquals(5, lookup.getCount());
        } finally {
            pointMaintainService.deleteIfExists(parentPoint.getKey());
            for (AlarmHistory alarmHistory : alarmHistories) {
                alarmHistoryMaintainService.deleteIfExists(alarmHistory.getKey());
            }
            alarmSettingMaintainService.deleteIfExists(parentAlarmSetting.getKey());
        }
    }
}
