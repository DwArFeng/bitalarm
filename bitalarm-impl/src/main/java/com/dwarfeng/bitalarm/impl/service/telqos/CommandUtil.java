package com.dwarfeng.bitalarm.impl.service.telqos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 指令工具类。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
final class CommandUtil {

    private static final int STRING_LENGTH_NULL = 4;

    // 为了项目的可扩展性，保留此方法。
    @SuppressWarnings("unused")
    public static int maxStringLength(@Nonnull List<String> stringList, int offset) {
        int result = 0;
        for (String string : stringList) {
            int currentLength = Objects.isNull(string) ? STRING_LENGTH_NULL : string.length();
            result = Math.max(result, currentLength);
        }
        return result + offset;
    }

    // 为了项目的可扩展性，保留此方法。
    @SuppressWarnings("unused")
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
