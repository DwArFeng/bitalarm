package com.dwarfeng.bitalarm.impl.service.telqos;

import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlarmCommand extends CliCommand {

    private static final String IDENTITY = "alarm";
    private static final String DESCRIPTION = "报警功能上线/下线";
    private static final String CMD_LINE_SYNTAX_ONLINE = "alarm -online";
    private static final String CMD_LINE_SYNTAX_OFFLINE = "alarm -offline";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_ONLINE + System.lineSeparator() +
            CMD_LINE_SYNTAX_OFFLINE;

    public AlarmCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private AlarmQosService alarmQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("online").optionalArg(true).hasArg(true).desc("上线服务").build());
        list.add(Option.builder("offline").optionalArg(true).hasArg(true).desc("下线服务").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -online -offline");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "online":
                    alarmQosService.startAlarm();
                    context.sendMessage("记录功能已上线!");
                    break;
                case "offline":
                    alarmQosService.stopAlarm();
                    context.sendMessage("记录功能已下线!");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("online")) {
            i++;
            subCmd = "online";
        }
        if (cmd.hasOption("offline")) {
            i++;
            subCmd = "offline";
        }
        return Pair.of(subCmd, i);
    }
}
