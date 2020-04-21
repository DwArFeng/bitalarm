package com.dwarfeng.bitalarm.stack.handler;

import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 消费处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ConsumeHandler<E extends Bean> extends Handler {

    /**
     * 消费处理器是否启动。
     *
     * @return 消费处理器是否启动。
     * @throws HandlerException 处理器异常。
     */
    boolean isStart() throws HandlerException;

    /**
     * 开启消费处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 关闭消费处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 使消费处理器接受指定的元素。
     *
     * @param element 指定的元素。
     * @throws HandlerException 处理器异常。
     */
    void accept(E element) throws HandlerException;

    /**
     * 获取缓冲器的容量。
     *
     * @return 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    int getBufferSize() throws HandlerException;

    /**
     * 获取数据的批处理量。
     *
     * @return 数据的批处理量。
     * @throws HandlerException 处理器异常。
     */
    int getBatchSize() throws HandlerException;

    /**
     * 获取最大空闲时间。
     *
     * @return 最大空闲时间。
     * @throws HandlerException 处理器异常。
     */
    long getMaxIdleTime() throws HandlerException;

    /**
     * 设置缓冲器的参数。
     *
     * @param bufferSize  缓冲器的大小。
     * @param batchSize   数据的批处理量。
     * @param maxIdleTime 最大空闲时间。
     * @throws HandlerException 处理器异常。
     */
    void setBufferParameters(int bufferSize, int batchSize, long maxIdleTime) throws HandlerException;

    /**
     * 获取消费者的线程数量。
     *
     * @return 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    int getThread() throws HandlerException;

    /**
     * 设置消费者的线程数量。
     *
     * @param thread 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    void setThread(int thread) throws HandlerException;

    /**
     * 获取消费者是否空闲。
     *
     * @return 消费者是否空闲。
     * @throws HandlerException 处理器异常。
     */
    boolean isIdle() throws HandlerException;
}
