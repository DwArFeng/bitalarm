package com.dwarfeng.bitalarm.impl.handler.pusher;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.5.3
 */
@Component
public class DrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "drain";

    public DrainPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void alarmUpdated(AlarmInfo alarmInfo) {
    }

    @Override
    public void alarmUpdated(List<AlarmInfo> alarmInfos) {
    }

    @Override
    public void historyRecorded(AlarmHistory alarmHistory) {
    }

    @Override
    public void historyRecorded(List<AlarmHistory> alarmHistories) {
    }

    @Override
    public void alarmReset() {
    }
}
