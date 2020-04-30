package com.dwarfeng.bitalarm.impl.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.bitalarm.stack.service.AlarmTypeIndicatorMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.ByteIdKey;
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
public class AlarmTypeIndicatorMaintainServiceImplTest {

    @Autowired
    private AlarmTypeIndicatorMaintainService alarmTypeIndicatorMaintainService;

    private List<AlarmTypeIndicator> alarmTypeIndicators;

    @Before
    public void setUp() {
        alarmTypeIndicators = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AlarmTypeIndicator alarmTypeIndicator = new AlarmTypeIndicator(
                    new ByteIdKey((byte) i),
                    "测试 " + i
            );
            alarmTypeIndicators.add(alarmTypeIndicator);
        }
    }

    @After
    public void tearDown() {
        alarmTypeIndicators.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (AlarmTypeIndicator alarmTypeIndicator : alarmTypeIndicators) {
                alarmTypeIndicatorMaintainService.insertOrUpdate(alarmTypeIndicator);
                alarmTypeIndicatorMaintainService.update(alarmTypeIndicator);
                AlarmTypeIndicator testAlarmTypeIndicator = alarmTypeIndicatorMaintainService.get(alarmTypeIndicator.getKey());
                assertEquals(BeanUtils.describe(alarmTypeIndicator), BeanUtils.describe(testAlarmTypeIndicator));
            }
        } finally {
            for (AlarmTypeIndicator alarmTypeIndicator : alarmTypeIndicators) {
                alarmTypeIndicatorMaintainService.deleteIfExists(alarmTypeIndicator.getKey());
            }
        }
    }
}
