package com.dwarfeng.bitalarm.impl.handler.consumer;

import com.dwarfeng.bitalarm.impl.handler.Consumer;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.handler.PushHandler;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryRecordEventConsumer implements Consumer<AlarmHistory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryRecordEventConsumer.class);

    @Autowired
    private PushHandler pushHandler;

    @Override
    public void consume(List<AlarmHistory> alarmHistories) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            try {
                pushHandler.historyRecorded(alarmHistories);
                return;
            } catch (Exception e) {
                LOGGER.error("数据推送失败, 试图使用不同的策略进行推送: 逐条推送", e);
            }

            List<AlarmHistory> failedList = new ArrayList<>();

            for (AlarmHistory alarmHistory : alarmHistories) {
                try {
                    pushHandler.historyRecorded(alarmHistories);
                } catch (Exception e) {
                    LOGGER.error("数据推送失败, 放弃对数据的推送: " + alarmHistory, e);
                    failedList.add(alarmHistory);
                }
            }

            if (!failedList.isEmpty()) {
                LOGGER.error("推送数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                failedList.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
            }
        } finally {
            tm.stop();
            LOGGER.info("消费者处理了 " + alarmHistories.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
        }
    }
}
