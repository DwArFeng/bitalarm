package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.handler.AlarmHandler;
import com.dwarfeng.bitalarm.stack.handler.AlarmLocalCacheHandler;
import com.dwarfeng.bitalarm.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final AlarmHandler alarmHandler;
    private final AlarmLocalCacheHandler alarmLocalCacheHandler;

    private final PushHandler pushHandler;

    public ResetProcessor(
            AlarmHandler alarmHandler,
            AlarmLocalCacheHandler alarmLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.alarmHandler = alarmHandler;
        this.alarmLocalCacheHandler = alarmLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetAlarm() throws HandlerException {
        // 获取当前的报警处理器的状态。
        boolean started = alarmHandler.isStarted();

        // 报警处理器停止，且清空本地缓存。
        alarmHandler.stop();
        alarmLocalCacheHandler.clear();

        // 如果报警处理器之前是启动的，则重新启动。
        if (started) {
            alarmHandler.start();
        }

        try {
            pushHandler.alarmReset();
        } catch (Exception e) {
            LOGGER.warn("推送报警功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
