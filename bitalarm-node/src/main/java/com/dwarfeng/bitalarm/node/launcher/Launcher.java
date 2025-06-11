package com.dwarfeng.bitalarm.node.launcher;

import com.dwarfeng.bitalarm.node.handler.LauncherSettingHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.bitalarm.stack.service.ResetQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
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
            // 根据启动器设置处理器的设置，选择性开启报警服务。
            mayStartAlarm(ctx);

            // 根据启动器设置处理器的设置，选择性开启重置服务。
            mayStartReset(ctx);
        });
    }

    private static void mayStartAlarm(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理报警处理器的启动选项。
        AlarmQosService alarmQosService = ctx.getBean(AlarmQosService.class);

        // 判断是否开启报警服务。
        long startAlarmDelay = launcherSettingHandler.getStartAlarmDelay();
        if (startAlarmDelay == 0) {
            LOGGER.info("立即启动报警服务...");
            try {
                alarmQosService.startAlarm();
            } catch (ServiceException e) {
                LOGGER.error("无法启动报警服务，异常原因如下", e);
            }
        } else if (startAlarmDelay > 0) {
            LOGGER.info("{} 毫秒后启动报警服务...", startAlarmDelay);
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
    }

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理重置处理器的启动选项。
        ResetQosService resetQosService = ctx.getBean(ResetQosService.class);

        // 重置处理器是否启动重置服务。
        long startResetDelay = launcherSettingHandler.getStartResetDelay();
        if (startResetDelay == 0) {
            LOGGER.info("立即启动重置服务...");
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动重置服务，异常原因如下", e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info("{} 毫秒后启动重置服务...", startResetDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动重置服务...");
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动重置服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startResetDelay)
            );
        }
    }
}
