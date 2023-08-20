package com.dwarfeng.bitalarm.impl.service.telqos;

import com.dwarfeng.bitalarm.stack.service.AlarmQosService;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerId;
import com.dwarfeng.bitalarm.stack.service.AlarmQosService.ConsumerStatus;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@TelqosCommand
public class ConsumerCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerCommand.class);

    private static final String COMMAND_OPTION_L = "l";
    private static final String COMMAND_OPTION_S = "s";
    private static final String COMMAND_OPTION_LC = "lc";
    private static final String COMMAND_OPTION_LN = "ln";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_L,
            COMMAND_OPTION_S,
            COMMAND_OPTION_LC,
            COMMAND_OPTION_LN
    };

    private static final String COMMAND_OPTION_H = "h";

    private static final String IDENTITY = "csu";
    private static final String DESCRIPTION = "消费者操作";
    private static final String CMD_LINE_SYNTAX_L = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_L) + " [-c classes] [-n names] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_H) + "]";
    private static final String CMD_LINE_SYNTAX_S = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_S) +
            " [-c classes] [-n names] [-b val] [-a val] [-m val] [-t val]";
    private static final String CMD_LINE_SYNTAX_LC = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LC);
    private static final String CMD_LINE_SYNTAX_LN = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LN);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_L,
            CMD_LINE_SYNTAX_S,
            CMD_LINE_SYNTAX_LC,
            CMD_LINE_SYNTAX_LN
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final AlarmQosService alarmQosService;

    private final ThreadPoolTaskScheduler scheduler;

    public ConsumerCommand(
            AlarmQosService alarmQosService,
            ThreadPoolTaskScheduler scheduler
    ) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.alarmQosService = alarmQosService;
        this.scheduler = scheduler;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_L).optionalArg(true).hasArg(false).desc("查看消费者状态").build());
        list.add(Option.builder(COMMAND_OPTION_H).desc("持续输出").build());
        list.add(Option.builder(COMMAND_OPTION_S).optionalArg(true).hasArg(false).desc("设置消费者参数").build());
        list.add(Option.builder(COMMAND_OPTION_LC).longOpt("list-classes").optionalArg(true).hasArg(false)
                .desc("列出所有消费者类型").build());
        list.add(Option.builder(COMMAND_OPTION_LN).longOpt("list-names").optionalArg(true).hasArg(false)
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
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_L:
                    handleL(context, cmd);
                    break;
                case COMMAND_OPTION_S:
                    handleS(context, cmd);
                    break;
                case COMMAND_OPTION_LC:
                    handleLc(context);
                    break;
                case COMMAND_OPTION_LN:
                    handleLn(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleL(Context context, CommandLine cmd) throws Exception {
        List<ConsumerId> consumerIds = parserConsumerIds(cmd);

        // 如果命令行中包含 COMMAND_OPTION_H 选项，则持续输出。
        if (cmd.hasOption(COMMAND_OPTION_H)) {
            ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(
                    () -> {
                        try {
                            printConsumerStatus(context, consumerIds);
                            // 如果打印的内容多于 1 行，则输出一个空行。
                            if (consumerIds.size() > 1) {
                                context.sendMessage("");
                            }
                        } catch (Exception e) {
                            LOGGER.warn("持续输出消费者状态时发生异常, 异常信息如下: ", e);
                        }
                    },
                    1000
            );
            // 等待用户输入任意字符，然后停止持续输出。
            context.sendMessage("输入任意字符停止持续输出");
            context.receiveMessage();
            future.cancel(true);
        } else {
            printConsumerStatus(context, consumerIds);
        }
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

    private void printConsumerStatus(Context context, List<ConsumerId> consumerIds)
            throws ServiceException, TelqosException {
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
}
