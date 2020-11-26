package com.dwarfeng.bitalarm.impl.handler.consumer;

import com.dwarfeng.bitalarm.impl.handler.Consumer;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@SuppressWarnings("DuplicatedCode")
@Component
public class AlarmInfoValueConsumer implements Consumer<AlarmInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoValueConsumer.class);

    @Autowired
    private AlarmInfoMaintainService alarmInfoMaintainService;

    @Override
    public void consume(List<AlarmInfo> alarmInfos) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            Map<LongIdKey, AlarmInfo> alarmInfoMap = new HashMap<>();
            try {
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
            } catch (Exception e) {
                LOGGER.error("处理数据时发生异常, 最多 " + alarmInfos.size() + " 个数据信息丢失", e);
                alarmInfos.forEach(alarmInfo -> LOGGER.debug(alarmInfo + ""));
                return;
            }

            List<AlarmInfo> failedList = new ArrayList<>();

            for (AlarmInfo alarmInfo : alarmInfoMap.values()) {
                try {
                    AlarmInfo ifExists = alarmInfoMaintainService.getIfExists(alarmInfo.getKey());
                    if (Objects.isNull(ifExists)) {
                        alarmInfoMaintainService.insert(alarmInfo);
                    } else {
                        int compareResult = alarmInfo.getHappenedDate().compareTo(ifExists.getHappenedDate());
                        if (compareResult > 0) {
                            alarmInfoMaintainService.update(alarmInfo);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("数据插入失败, 放弃对数据的推送: " + alarmInfo, e);
                    failedList.add(alarmInfo);
                }
            }

            if (!failedList.isEmpty()) {
                LOGGER.error("记录数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                failedList.forEach(alarmInfo -> LOGGER.debug(alarmInfo + ""));
            }
        } finally {
            tm.stop();
            LOGGER.debug("消费者处理了 " + alarmInfos.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
        }
    }
}
