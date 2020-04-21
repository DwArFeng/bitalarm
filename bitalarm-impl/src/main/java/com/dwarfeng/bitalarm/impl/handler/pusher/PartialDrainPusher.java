package com.dwarfeng.bitalarm.impl.handler.pusher;

import com.dwarfeng.bitalarm.impl.handler.Pusher;
import com.dwarfeng.bitalarm.stack.bean.dto.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

/**
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class PartialDrainPusher implements Pusher {

    public static final String SUPPORT_TYPE = "partial_drain";

    @Autowired
    private List<Pusher> pushers;

    @Value("${pusher.partial_drain.delegate_type}")
    private String delegateType;
    @Value("${pusher.partial_drain.drain_alarm_appeared}")
    private boolean drainAlarmAppeared;
    @Value("${pusher.partial_drain.drain_alarm_disappeared}")
    private boolean drainAlarmDisappeared;
    @Value("${pusher.partial_drain.drain_history_recorded}")
    private boolean drainHistoryRecorded;

    private Pusher delegate;

    @PostConstruct
    public void init() throws HandlerException {
        this.delegate = pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType));
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public void alarmAppeared(AlarmInfo alarmInfo) throws HandlerException {
        if (!drainAlarmAppeared) {
            delegate.alarmAppeared(alarmInfo);
        }
    }

    @Override
    public void alarmAppeared(List<AlarmInfo> alarmInfos) throws HandlerException {
        if (!drainAlarmAppeared) {
            delegate.alarmAppeared(alarmInfos);
        }
    }

    @Override
    public void alarmDisappeared(AlarmInfo alarmInfo) throws HandlerException {
        if (!drainAlarmDisappeared) {
            delegate.alarmDisappeared(alarmInfo);
        }
    }

    @Override
    public void alarmDisappeared(List<AlarmInfo> alarmInfos) throws HandlerException {
        if (!drainAlarmDisappeared) {
            delegate.alarmDisappeared(alarmInfos);
        }
    }

    @Override
    public void historyRecorded(AlarmHistory alarmHistory) throws HandlerException {
        if (!drainHistoryRecorded) {
            delegate.historyRecorded(alarmHistory);
        }
    }

    @Override
    public void historyRecorded(List<AlarmHistory> alarmHistories) throws HandlerException {
        if (!drainHistoryRecorded) {
            delegate.historyRecorded(alarmHistories);
        }
    }

    public Pusher getDelegate() {
        return delegate;
    }

    public void setDelegate(Pusher delegate) {
        this.delegate = delegate;
    }

    public boolean isDrainAlarmAppeared() {
        return drainAlarmAppeared;
    }

    public void setDrainAlarmAppeared(boolean drainAlarmAppeared) {
        this.drainAlarmAppeared = drainAlarmAppeared;
    }

    public boolean isDrainAlarmDisappeared() {
        return drainAlarmDisappeared;
    }

    public void setDrainAlarmDisappeared(boolean drainAlarmDisappeared) {
        this.drainAlarmDisappeared = drainAlarmDisappeared;
    }

    public boolean isDrainHistoryRecorded() {
        return drainHistoryRecorded;
    }

    public void setDrainHistoryRecorded(boolean drainHistoryRecorded) {
        this.drainHistoryRecorded = drainHistoryRecorded;
    }
}
