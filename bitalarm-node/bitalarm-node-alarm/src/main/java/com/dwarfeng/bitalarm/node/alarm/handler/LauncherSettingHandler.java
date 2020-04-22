package com.dwarfeng.bitalarm.node.alarm.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.start_alarm_delay}")
    private long startAlarmDelay;

    public long getStartAlarmDelay() {
        return startAlarmDelay;
    }
}
