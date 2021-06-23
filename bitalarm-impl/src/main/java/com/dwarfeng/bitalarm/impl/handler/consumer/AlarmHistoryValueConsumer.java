package com.dwarfeng.bitalarm.impl.handler.consumer;

import com.dwarfeng.bitalarm.impl.handler.Consumer;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
@Component
public class AlarmHistoryValueConsumer implements Consumer<AlarmHistory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmHistoryValueConsumer.class);

    @Autowired
    private AlarmHistoryMaintainService alarmHistoryMaintainService;

    @Override
    public void consume(List<AlarmHistory> alarmHistories) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            try {
                alarmHistoryMaintainService.batchInsertOrUpdate(alarmHistories);
                return;
            } catch (Exception e) {
                LOGGER.warn("数据插入失败, 试图使用不同的策略进行插入: 逐条插入", e);
            }

            List<AlarmHistory> failedList = new ArrayList<>();

            for (AlarmHistory alarmHistory : alarmHistories) {
                try {
                    alarmHistoryMaintainService.insertOrUpdate(alarmHistory);
                } catch (Exception e) {
                    LOGGER.error("数据插入失败, 放弃对数据的插入: " + alarmHistory, e);
                    failedList.add(alarmHistory);
                }
            }

            if (!failedList.isEmpty()) {
                LOGGER.error("记录数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                failedList.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
            }
        } finally {
            tm.stop();
            LOGGER.debug("消费者处理了 " + alarmHistories.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
        }
    }
}
