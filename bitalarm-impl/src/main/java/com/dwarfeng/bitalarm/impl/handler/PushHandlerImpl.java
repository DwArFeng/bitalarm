package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(List<Pusher> pushers) {
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + pusherType));
    }

    @Override
    public void alarmUpdated(AlarmInfo alarmInfo) throws HandlerException {
        pusher.alarmUpdated(alarmInfo);
    }

    @Override
    public void alarmUpdated(List<AlarmInfo> alarmInfos) throws HandlerException {
        pusher.alarmUpdated(alarmInfos);
    }

    @Override
    public void historyRecorded(AlarmHistory alarmHistory) throws HandlerException {
        pusher.historyRecorded(alarmHistory);
    }

    @Override
    public void historyRecorded(List<AlarmHistory> alarmHistories) throws HandlerException {
        pusher.historyRecorded(alarmHistories);
    }
}
