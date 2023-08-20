package com.dwarfeng.bitalarm.impl.handler.source;

import com.dwarfeng.bitalarm.stack.handler.Source;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据源的抽象实现。
 *
 * @author DwArFeng
 * @since 1.7.0
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
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
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
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
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
