package com.dwarfeng.bitalarm.node.launcher;

import com.dwarfeng.bitalarm.node.handler.LauncherSettingHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

            // 拿出程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
            ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

            // 判断是否开启报警服务。
            long startAlarmDelay = launcherSettingHandler.getStartAlarmDelay();
            AlarmQosService alarmQosService = ctx.getBean(AlarmQosService.class);
            if (startAlarmDelay == 0) {
                LOGGER.info("立即启动报警服务...");
                try {
                    alarmQosService.startAlarm();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动报警服务，异常原因如下", e);
                }
            } else if (startAlarmDelay > 0) {
                LOGGER.info(startAlarmDelay + " 毫秒后启动报警服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("启动报警服务...");
                            try {
                                alarmQosService.startAlarm();
                            } catch (ServiceException e) {
                                LOGGER.error("无法启动报警服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + startAlarmDelay)
                );
            }
        });
    }
}
