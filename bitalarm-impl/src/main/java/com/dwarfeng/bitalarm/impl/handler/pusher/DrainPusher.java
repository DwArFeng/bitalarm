package com.dwarfeng.bitalarm.impl.handler.pusher;

import com.dwarfeng.bitalarm.impl.handler.Pusher;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainPusher implements Pusher {

    public static final String SUPPORT_TYPE = "drain";

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
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
}
