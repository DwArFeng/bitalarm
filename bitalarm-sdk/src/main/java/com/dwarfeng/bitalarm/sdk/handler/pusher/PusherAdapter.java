package com.dwarfeng.bitalarm.sdk.handler.pusher;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 推送器适配器。
 *
 * <p>
 * 该类是一个适配器，对所有的事件推送方法均进行空实现。
 *
 * <p>
 * 所有的插件实现推送器时都建议继承该类，这样做的好处是：
 * <ul>
 *     <li>适配器对所有的事件推送方法均进行空实现，因此插件实现推送器时只需要重写需要的方法即可。</li>
 *     <li>当服务版本升级，增加新事件时，旧的推送器实现无须增加代码，因为新的事件推送方法在适配器中已经有了空实现。</li>
 * </ul>
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public abstract class PusherAdapter extends AbstractPusher {

    public PusherAdapter() {
    }

    public PusherAdapter(String pusherType) {
        super(pusherType);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void alarmUpdated(AlarmInfo alarmInfo) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void alarmUpdated(List<AlarmInfo> alarmInfos) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void historyRecorded(AlarmHistory alarmHistory) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void historyRecorded(List<AlarmHistory> alarmHistories) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void alarmReset() throws HandlerException {
    }
}
