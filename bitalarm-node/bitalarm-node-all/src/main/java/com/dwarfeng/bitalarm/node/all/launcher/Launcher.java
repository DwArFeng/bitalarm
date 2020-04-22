package com.dwarfeng.bitalarm.node.all.launcher;

import com.dwarfeng.bitalarm.node.all.handler.LauncherSettingHandler;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.springterminator.stack.handler.Terminator;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml");
        ctx.registerShutdownHook();
        ctx.start();
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
        // 判断是否开启报警处理服务。
        long startAlarmDelay = launcherSettingHandler.getStartAlarmDelay();
        AlarmQosService alarmQosService = ctx.getBean(AlarmQosService.class);
        if (startAlarmDelay == 0) {
            LOGGER.info("立即启动报警处理服务...");
            try {
                alarmQosService.startAlarm();
            } catch (ServiceException e) {
                LOGGER.error("无法启动报警处理服务，异常原因如下", e);
            }
        } else if (startAlarmDelay > 0) {
            LOGGER.info(startAlarmDelay + " 毫秒后启动报警处理服务...");
            try {
                Thread.sleep(startAlarmDelay);
            } catch (InterruptedException ignored) {
            }
            LOGGER.info("启动报警处理服务...");
            try {
                alarmQosService.startAlarm();
            } catch (ServiceException e) {
                LOGGER.error("无法启动报警处理服务，异常原因如下", e);
            }
        }
        Terminator terminator = ctx.getBean(Terminator.class);
        System.exit(terminator.getExitCode());
    }
}
