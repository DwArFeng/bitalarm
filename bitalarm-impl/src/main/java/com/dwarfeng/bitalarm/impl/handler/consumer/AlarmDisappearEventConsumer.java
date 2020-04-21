package com.dwarfeng.bitalarm.impl.handler.consumer;

import com.dwarfeng.bitalarm.impl.handler.Consumer;
import com.dwarfeng.bitalarm.stack.bean.dto.AlarmInfo;
import com.dwarfeng.bitalarm.stack.handler.PushHandler;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AlarmDisappearEventConsumer implements Consumer<AlarmInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDisappearEventConsumer.class);

    @Autowired
    private PushHandler pushHandler;

    @Override
    public void consume(List<AlarmInfo> alarmInfos) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            Map<LongIdKey, AlarmInfo> alarmInfoMap = new HashMap<>();
            for (AlarmInfo alarmInfo : alarmInfos) {
                if (alarmInfoMap.containsKey(alarmInfo.getKey())) {
                    int compareResult = alarmInfo.getHappenedDate()
                            .compareTo(alarmInfoMap.get(alarmInfo.getKey()).getHappenedDate());
                    if (compareResult > 0) {
                        alarmInfoMap.put(alarmInfo.getKey(), alarmInfo);
                    }
                } else {
                    alarmInfoMap.put(alarmInfo.getKey(), alarmInfo);
                }
            }

            try {
                pushHandler.alarmDisappeared(new ArrayList<>(alarmInfoMap.values()));
                return;
            } catch (Exception e) {
                LOGGER.error("数据推送失败, 试图使用不同的策略进行推送: 逐条推送", e);
            }

            List<AlarmInfo> failedList = new ArrayList<>();

            for (AlarmInfo alarmInfo : alarmInfoMap.values()) {
                try {
                    pushHandler.alarmDisappeared(alarmInfo);
                } catch (Exception e) {
                    LOGGER.error("数据推送失败, 放弃对数据的推送: " + alarmInfo, e);
                    failedList.add(alarmInfo);
                }
            }

            if (!failedList.isEmpty()) {
                LOGGER.error("推送数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                failedList.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
            }
        } finally {
            tm.stop();
            LOGGER.info("消费者处理了 " + alarmInfos.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
        }
    }
}
