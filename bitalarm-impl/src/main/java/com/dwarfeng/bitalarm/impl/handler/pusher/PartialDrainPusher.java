package com.dwarfeng.bitalarm.impl.handler.pusher;

import com.dwarfeng.bitalarm.impl.handler.Pusher;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class PartialDrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "partial_drain";

    @Autowired
    private List<Pusher> pushers;

    @Value("${pusher.partial_drain.delegate_type}")
    private String delegateType;
    @Value("${pusher.partial_drain.drain_alarm_updated}")
    private boolean drainAlarmUpdated;
    @Value("${pusher.partial_drain.drain_history_recorded}")
    private boolean drainHistoryRecorded;

    private Pusher delegate;

    public PartialDrainPusher() {
        super(PUSHER_TYPE);
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.delegate = pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType));
    }

    @Override
    public void alarmUpdated(AlarmInfo alarmInfo) throws HandlerException {
        if (!drainAlarmUpdated) {
            delegate.alarmUpdated(alarmInfo);
        }
    }

    @Override
    public void alarmUpdated(List<AlarmInfo> alarmInfos) throws HandlerException {
        if (!drainAlarmUpdated) {
            delegate.alarmUpdated(alarmInfos);
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

    public boolean isDrainAlarmUpdated() {
        return drainAlarmUpdated;
    }

    public void setDrainAlarmUpdated(boolean drainAlarmUpdated) {
        this.drainAlarmUpdated = drainAlarmUpdated;
    }

    public boolean isDrainHistoryRecorded() {
        return drainHistoryRecorded;
    }

    public void setDrainHistoryRecorded(boolean drainHistoryRecorded) {
        this.drainHistoryRecorded = drainHistoryRecorded;
    }
}
