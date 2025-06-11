package com.dwarfeng.bitalarm.impl.service.telqos;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 指令工具类。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
final class CommandUtil {

    private static final int STRING_LENGTH_NULL = 4;

    /**
     * 拼接选项的前缀，用于生成选项说明书。
     *
     * <p>
     * online -> -online<br>
     * dump-file -> --dump-file。
     *
     * @param commandOption 指定的选项。
     * @return 拼接前缀之后的选项。
     */
    public static String concatOptionPrefix(@NotNull String commandOption) {
        if (commandOption.contains("-")) {
            return "--" + commandOption;
        }
        return "-" + commandOption;
    }

    public static String syntax(@Nonnull String... patterns) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (String pattern : patterns) {
            sj.add(pattern);
        }
        return sj.toString();
    }

    public static Pair<String, Integer> analyseCommand(
            @Nonnull CommandLine commandLine, @Nonnull String... commandOptions
    ) {
        int i = 0;
        String subCmd = null;
        for (String commandOption : commandOptions) {
            if (commandLine.hasOption(commandOption)) {
                i++;
                subCmd = commandOption;
            }
        }
        return Pair.of(subCmd, i);
    }

    public static String optionMismatchMessage(@Nonnull String... patterns) {
        StringJoiner sj = new StringJoiner(", ", "下列选项必须且只能含有一个: ", "");
        for (String pattern : patterns) {
            sj.add(concatOptionPrefix(pattern));
        }
        return sj.toString();
    }

    public static int maxStringLength(@Nonnull List<String> stringList, int offset) {
        int result = 0;
        for (String string : stringList) {
            int currentLength = Objects.isNull(string) ? STRING_LENGTH_NULL : string.length();
            result = Math.max(result, currentLength);
        }
        return result + offset;
    }

    public static String formatDate(@Nullable Date date) {
        if (Objects.isNull(date)) {
            return "null";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 获取一个数字在十进制下的字符串长度。
     *
     * @param number 指定的数字。
     * @return 指定数字在十进制下的字符串长度。
     */
    public static int decimalStringLength(int number) {
        if (number == 0) {
            return 1;
        }
        return (int) (Math.log10(number) + 1);
    }

    private CommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
