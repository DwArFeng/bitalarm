package com.dwarfeng.bitalarm.impl.handler.pusher;

import com.dwarfeng.bitalarm.impl.handler.Pusher;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 同时将消息推送给所有代理的多重推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MultiPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "multi";

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPusher.class);

    private final List<Pusher> pushers;

    @Value("${pusher.multi.delegate_types}")
    private String delegateTypes;

    private final List<Pusher> delegates = new ArrayList<>();

    public MultiPusher(
            // 使用懒加载，避免循环依赖。
            @Lazy List<Pusher> pushers
    ) {
        super(PUSHER_TYPE);
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        StringTokenizer st = new StringTokenizer(delegateTypes, ",");
        while (st.hasMoreTokens()) {
            String delegateType = st.nextToken();
            delegates.add(pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                    .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType)));
        }
    }

    @Override
    public void alarmUpdated(AlarmInfo alarmInfo) {
        for (Pusher delegate : delegates) {
            try {
                delegate.alarmUpdated(alarmInfo);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void alarmUpdated(List<AlarmInfo> alarmInfos) {
        for (Pusher delegate : delegates) {
            try {
                delegate.alarmUpdated(alarmInfos);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void historyRecorded(AlarmHistory alarmHistory) {
        for (Pusher delegate : delegates) {
            try {
                delegate.historyRecorded(alarmHistory);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void historyRecorded(List<AlarmHistory> alarmHistories) {
        for (Pusher delegate : delegates) {
            try {
                delegate.historyRecorded(alarmHistories);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }
}
