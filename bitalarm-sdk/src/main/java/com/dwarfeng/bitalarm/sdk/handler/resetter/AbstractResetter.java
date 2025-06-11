package com.dwarfeng.bitalarm.sdk.handler.resetter;

import com.dwarfeng.bitalarm.stack.handler.Resetter;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 重置器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public abstract class AbstractResetter implements Resetter {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void start() throws HandlerException {
        try {
            doStart();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 启动方法的具体实现。
     *
     * @throws Exception 启动过程中发生的任何异常。
     * @see Resetter#start()
     */
    protected abstract void doStart() throws Exception;

    @Override
    public void stop() throws HandlerException {
        try {
            doStop();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 停止方法的具体实现。
     *
     * @throws Exception 停止过程中发生的任何异常。
     * @see Resetter#stop()
     */
    protected abstract void doStop() throws Exception;

    @Override
    public String toString() {
        return "AbstractResetter{" +
                "context=" + context +
                '}';
    }
}
