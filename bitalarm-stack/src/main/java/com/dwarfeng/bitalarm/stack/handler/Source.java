package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Date;

/**
 * 数据源。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Source {

    /**
     * 初始化数据源。
     *
     * <p>
     * 该方法会在数据源初始化后调用，请将 context 存放在数据源的字段中。<br>
     * 当数据源被触发后，执行上下文中的相应方法即可。
     *
     * @param context 数据源的上下文。
     * @since 1.7.0
     */
    void init(Context context);

    /**
     * 数据源是否上线。
     *
     * @return 数据源是否上线。
     * @throws HandlerException 处理器异常。
     */
    boolean isOnline() throws HandlerException;

    /**
     * 数据源上线。
     * <p>该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void online() throws HandlerException;

    /**
     * 数据源下线。
     * <p>该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void offline() throws HandlerException;

    /**
     * 数据源上下文。
     *
     * @author DwArFeng
     * @since 1.7.0
     */
    interface Context {

        /**
         * 处理指定的报警信息。
         *
         * @param pointKey     数据点的主键。
         * @param data         详细的报警数据。
         * @param happenedDate 报警的发生时间。
         * @throws HandlerException 处理器异常。
         */
        void processAlarm(LongIdKey pointKey, byte[] data, Date happenedDate) throws HandlerException;
    }
}
