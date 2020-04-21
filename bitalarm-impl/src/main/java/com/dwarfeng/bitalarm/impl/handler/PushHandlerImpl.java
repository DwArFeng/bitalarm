package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PushHandlerImpl implements PushHandler {

    @Autowired(required = false)
    @SuppressWarnings("FieldMayBeFinal")
    private List<Pusher> pushers = new ArrayList<>();

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

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
