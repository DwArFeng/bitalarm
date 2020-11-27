package com.dwarfeng.bitalarm.impl.service.telqos;

import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerId;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerStatus;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConsumerCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerCommand.class);

    private static final String IDENTITY = "csu";
    private static final String DESCRIPTION = "消费者操作";
    private static final String CMD_LINE_SYNTAX_L = "csu -l [-c classes] [-n names]";
    private static final String CMD_LINE_SYNTAX_S = "csu -s [-c classes] [-n names] [-b val] [-a val] [-m val] [-t val]";
    private static final String CMD_LINE_SYNTAX_LC = "csu -lc";
    private static final String CMD_LINE_SYNTAX_LN = "cus -ln";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_L + System.lineSeparator() +
            CMD_LINE_SYNTAX_S + System.lineSeparator() + CMD_LINE_SYNTAX_LC + System.lineSeparator() +
            CMD_LINE_SYNTAX_LN;

    public ConsumerCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private AlarmQosService alarmQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("l").optionalArg(true).hasArg(false).desc("查看消费者状态").build());
        list.add(Option.builder("s").optionalArg(true).hasArg(false).desc("设置消费者参数").build());
        list.add(Option.builder("lc").longOpt("list-classes").optionalArg(true).hasArg(false)
                .desc("列出所有消费者类型").build());
        list.add(Option.builder("ln").longOpt("list-names").optionalArg(true).hasArg(false)
                .desc("列出所有消费者名称").build());
        list.add(Option.builder("c").optionalArg(true).hasArg(true).argName("classes").desc("消费者类型").build());
        list.add(Option.builder("n").optionalArg(true).hasArg(true).argName("names").desc("消费者名称").build());
        list.add(Option.builder("b").optionalArg(true).hasArg(true).type(Number.class)
                .argName("buffer-size").desc("缓冲器的大小").build());
        list.add(Option.builder("a").optionalArg(true).hasArg(true).type(Number.class)
                .argName("batch-size").desc("数据的批处理量").build());
        list.add(Option.builder("m").optionalArg(true).hasArg(true).type(Number.class)
                .argName("max-idle-time").desc("最大空闲时间").build());
        list.add(Option.builder("t").optionalArg(true).hasArg(true).type(Number.class)
                .argName("thread").desc("消费者的线程数量").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -l -s -lc -ln");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "l":
                    handleL(context, cmd);
                    break;
                case "s":
                    handleS(context, cmd);
                    break;
                case "lc":
                    handleLc(context);
                    break;
                case "ln":
                    handleLn(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleL(Context context, CommandLine cmd) throws Exception {
        List<ConsumerId> consumerIds = parserConsumerIds(cmd);
        printConsumerStatus(context, consumerIds);
    }

    private void handleS(Context context, CommandLine cmd) throws Exception {
        List<ConsumerId> consumerIds = parserConsumerIds(cmd);
        Integer newBufferSize = null;
        Integer newBatchSize = null;
        Long newMaxIdleTime = null;
        Integer newThread = null;
        try {
            if (cmd.hasOption("b")) newBufferSize = Integer.parseInt(cmd.getOptionValue("b"));
            if (cmd.hasOption("a")) newBatchSize = Integer.parseInt(cmd.getOptionValue("a"));
            if (cmd.hasOption("m")) newMaxIdleTime = Long.parseLong(cmd.getOptionValue("m"));
            if (cmd.hasOption("t")) newThread = Integer.parseInt(cmd.getOptionValue("t"));
        } catch (Exception e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_S);
            context.sendMessage("请留意选项 b,a,m,t 后接参数的类型应该是数字 ");
            return;
        }
        for (ConsumerId consumerId : consumerIds) {
            ConsumerStatus consumerStatus = alarmQosService.getConsumerStatus(consumerId);
            int bufferSize = Objects.nonNull(newBufferSize) ? newBufferSize : consumerStatus.getBufferSize();
            int batchSize = Objects.nonNull(newBatchSize) ? newBatchSize : consumerStatus.getBatchSize();
            long maxIdleTime = Objects.nonNull(newMaxIdleTime) ? newMaxIdleTime : consumerStatus.getMaxIdleTime();
            int thread = Objects.nonNull(newThread) ? newThread : consumerStatus.getThread();
            alarmQosService.setConsumerParameters(consumerId, bufferSize, batchSize, maxIdleTime, thread);
        }
        context.sendMessage("设置完成，消费者新的参数为: ");
        printConsumerStatus(context, consumerIds);
    }

    private List<ConsumerId> parserConsumerIds(CommandLine cmd) {
        List<String> classValues = null;
        if (cmd.hasOption("c")) {
            classValues = Optional.ofNullable(cmd.getOptionValue("c")).map(s -> s.split(","))
                    .map(Arrays::asList).orElse(Collections.emptyList());
        }
        List<String> nameValues = null;
        if (cmd.hasOption("n")) {
            nameValues = Optional.ofNullable(cmd.getOptionValue("n")).map(s -> s.split(","))
                    .map(Arrays::asList).orElse(Collections.emptyList());
        }
        List<String> finalClassValues = classValues;
        List<String> finalNameValues = nameValues;
        return Arrays.stream(ConsumerId.values()).filter(id ->
                (Objects.isNull(finalClassValues) || finalClassValues.contains(id.getType())) &&
                        (Objects.isNull(finalNameValues) || finalNameValues.contains(id.getLabel()))
        ).collect(Collectors.toList());
    }

    private void printConsumerStatus(Context context, List<ConsumerId> consumerIds) throws ServiceException, TelqosException {
        int index = 0;
        for (ConsumerId consumerId : consumerIds) {
            ConsumerStatus consumerStatus = alarmQosService.getConsumerStatus(consumerId);
            context.sendMessage(
                    String.format(
                            "%-4d %s-%-14s buffered-size:%-7d buffer-size:%-7d batch-size:%-7d max-idle-time:%-10d " +
                                    "thread:%-3d idle:%b",
                            ++index, consumerId.getType(), consumerId.getLabel(), consumerStatus.getBufferedSize(),
                            consumerStatus.getBufferSize(), consumerStatus.getBatchSize(),
                            consumerStatus.getMaxIdleTime(), consumerStatus.getThread(), consumerStatus.isIdle())
            );
        }
    }

    private void handleLc(Context context) throws Exception {
        context.sendMessage("1.\tevent\t事件类型消费者");
        context.sendMessage("2.\tvalue\t值类型消费者");
    }

    private void handleLn(Context context) throws Exception {
        context.sendMessage("1.\talarm\t报警信息消费者");
        context.sendMessage("2.\thistory\t报警历史消费者");
    }

    @SuppressWarnings("DuplicatedCode")
    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("l")) {
            i++;
            subCmd = "l";
        }
        if (cmd.hasOption("s")) {
            i++;
            subCmd = "s";
        }
        if (cmd.hasOption("lc")) {
            i++;
            subCmd = "lc";
        }
        if (cmd.hasOption("ln")) {
            i++;
            subCmd = "ln";
        }
        return Pair.of(subCmd, i);
    }
}
