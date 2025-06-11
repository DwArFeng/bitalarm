package com.dwarfeng.bitalarm.impl.handler;

import com.dwarfeng.bitalarm.stack.handler.ResetHandler;
import com.dwarfeng.bitalarm.stack.handler.Resetter;
import com.dwarfeng.bitalarm.stack.handler.ResetterHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ResetHandlerImpl implements ResetHandler {

    private final GeneralStartableHandler startableHandler;

    private final ResetProcessor resetProcessor;

    private final Lock lock = new ReentrantLock();

    public ResetHandlerImpl(ResetWorker resetWorker, ResetProcessor resetProcessor) {
        this.startableHandler = new GeneralStartableHandler(resetWorker);
        this.resetProcessor = resetProcessor;
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }

    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void resetAlarm() throws HandlerException {
        lock.lock();
        try {
            resetProcessor.resetAlarm();
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static class ResetWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(ResetWorker.class);

        private final ResetterHandler resetterHandler;

        public ResetWorker(ResetterHandler resetterHandler) {
            this.resetterHandler = resetterHandler;
        }

        @Override
        public void work() throws Exception {
            List<Resetter> resetters = resetterHandler.all();
            LOGGER.info("启动重置器, 共 {} 个", resetters.size());
            for (Resetter resetter : resetters) {
                try {
                    resetter.start();
                } catch (Exception e) {
                    LOGGER.warn("重置器 {} 启动时发生异常, 将不会启动, 异常信息如下: ", resetter, e);
                }
            }
        }

        @Override
        public void rest() throws Exception {
            List<Resetter> resetters = resetterHandler.all();
            LOGGER.info("停止重置器, 共 {} 个", resetters.size());
            for (Resetter resetter : resetters) {
                try {
                    resetter.stop();
                } catch (Exception e) {
                    LOGGER.warn("重置器 {} 停止时发生异常, 将不会停止, 异常信息如下: ", resetter, e);
                }
            }
        }
    }
}
