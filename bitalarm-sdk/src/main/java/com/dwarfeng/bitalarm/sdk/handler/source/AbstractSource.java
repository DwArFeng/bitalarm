package com.dwarfeng.bitalarm.sdk.handler.source;

import com.dwarfeng.bitalarm.stack.handler.Source;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据源的抽象实现。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public abstract class AbstractSource implements Source {

    protected Context context;
    protected boolean onlineFlag = false;

    protected final Lock lock = new ReentrantLock();

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public boolean isOnline() {
        lock.lock();
        try {
            return onlineFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void online() throws HandlerException {
        lock.lock();
        try {
            doOnline();
            onlineFlag = true;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doOnline() throws Exception;

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            doOffline();
            onlineFlag = false;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doOffline() throws Exception;

    @Override
    public String toString() {
        return "AbstractSource{" +
                "context=" + context +
                '}';
    }
}
