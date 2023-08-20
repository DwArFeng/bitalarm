package com.dwarfeng.bitalarm.impl.service.telqos;

import com.dwarfeng.bitalarm.stack.handler.Resetter;
import com.dwarfeng.bitalarm.stack.service.ResetQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class ResetCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_START = "start";
    private static final String COMMAND_OPTION_STOP = "stop";
    private static final String COMMAND_OPTION_STATUS = "status";
    private static final String COMMAND_OPTION_RESET_ALARM = "reset-alarm";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_START,
            COMMAND_OPTION_STOP,
            COMMAND_OPTION_STATUS,
            COMMAND_OPTION_RESET_ALARM
    };

    private static final String IDENTITY = "reset";
    private static final String DESCRIPTION = "重置处理器操作/查看";

    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP);
    private static final String CMD_LINE_SYNTAX_START = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_START);
    private static final String CMD_LINE_SYNTAX_STOP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STOP);
    private static final String CMD_LINE_SYNTAX_STATUS = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS);
    private static final String CMD_LINE_SYNTAX_RESET_ALARM = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_ALARM);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_START,
            CMD_LINE_SYNTAX_STOP,
            CMD_LINE_SYNTAX_STATUS,
            CMD_LINE_SYNTAX_RESET_ALARM
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ResetQosService resetQosService;

    public ResetCommand(ResetQosService resetQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.resetQosService = resetQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_LOOKUP).desc("查看重置处理器").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_START).desc("启动重置处理器").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_STOP).desc("停止重置处理器").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_STATUS).desc("查看重置处理器状态").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_ALARM).desc("执行重置报警功能操作").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_LOOKUP:
                    printResetters(context);
                    break;
                case COMMAND_OPTION_START:
                    resetQosService.start();
                    context.sendMessage("重置处理器已启动!");
                    break;
                case COMMAND_OPTION_STOP:
                    resetQosService.stop();
                    context.sendMessage("重置处理器已停止!");
                    break;
                case COMMAND_OPTION_STATUS:
                    printStatus(context);
                    break;
                case COMMAND_OPTION_RESET_ALARM:
                    resetQosService.resetAlarm();
                    context.sendMessage("重置成功!");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void printResetters(Context context) throws Exception {
        List<Resetter> resetters = resetQosService.all();
        for (int i = 0; i < resetters.size(); i++) {
            context.sendMessage(String.format("%02d. %s", i + 1, resetters.get(i)));
        }
    }

    private void printStatus(Context context) throws Exception {
        boolean startedFlag = resetQosService.isStarted();
        context.sendMessage(String.format("started: %b", startedFlag));
    }
}
