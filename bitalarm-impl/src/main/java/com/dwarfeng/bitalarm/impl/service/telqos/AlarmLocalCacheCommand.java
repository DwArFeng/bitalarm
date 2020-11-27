package com.dwarfeng.bitalarm.impl.service.telqos;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AlarmLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmLocalCacheCommand.class);

    private static final String IDENTITY = "alc";
    private static final String DESCRIPTION = "数据记录本地缓存操作";
    private static final String CMD_LINE_SYNTAX_C = "alc -c";
    private static final String CMD_LINE_SYNTAX_P = "alc -p point-id";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_C + System.lineSeparator() + CMD_LINE_SYNTAX_P;

    public AlarmLocalCacheCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private AlarmQosService alarmQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("c").optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(Option.builder("p").optionalArg(true).hasArg(true).type(Number.class).argName("point-id")
                .desc("查看指定数据点的详细信息，如果本地缓存中不存在，则尝试抓取").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -c -p");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "c":
                    handleC(context);
                    break;
                case "p":
                    handleP(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleC(Context context) throws Exception {
        alarmQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    private void handleP(Context context, CommandLine cmd) throws Exception {
        long pointId;
        try {
            pointId = ((Number) cmd.getParsedOptionValue("p")).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_P);
            context.sendMessage("请留意选项 p 后接参数的类型应该是数字 ");
            return;
        }
        List<AlarmSetting> alarmSettings = alarmQosService.getAlarmSetting(new LongIdKey(pointId));
        if (Objects.isNull(alarmSettings)) {
            context.sendMessage("not exists!");
            return;
        }
        int digits = digits(alarmSettings.size());
        for (int i = 0; i < alarmSettings.size(); i++) {
            AlarmSetting alarmSetting = alarmSettings.get(i);
            context.sendMessage(String.format("%-" + digits + "d: %s", i, alarmSetting.toString()));
        }
    }

    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("c")) {
            i++;
            subCmd = "c";
        }
        if (cmd.hasOption("p")) {
            i++;
            subCmd = "p";
        }
        return Pair.of(subCmd, i);
    }

    private int digits(int target) {
        int digits = 0;
        do {
            digits++;
        } while ((target /= 10) > 0);
        return digits;
    }
}
