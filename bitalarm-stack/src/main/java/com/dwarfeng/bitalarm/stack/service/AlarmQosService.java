package com.dwarfeng.bitalarm.stack.service;

import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 服务质量服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmQosService extends Service {

    /**
     * 获取指定的数据点对应的所有报警设置。
     *
     * @param pointKey 指定的数据点主键。
     * @return 指定的数据点对应的所有报警设置。
     * @throws ServiceException 服务异常。
     */
    List<AlarmSetting> getAlarmSetting(LongIdKey pointKey) throws ServiceException;

    /**
     * 清除本地缓存。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 获取指定消费者的消费者状态。
     *
     * @param consumerId 指定的消费者ID。
     * @return 消费者状态。
     * @throws ServiceException 服务异常。
     */
    ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException;

    /**
     * 设置指定消费者的参数。
     *
     * @param consumerId  指定的消费者ID。
     * @param bufferSize  缓冲器的大小。
     * @param batchSize   数据的批处理量。
     * @param maxIdleTime 最大空闲时间。
     * @param thread      消费者的线程数量。
     * @throws ServiceException 服务异常。
     */
    void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException;

    /**
     * 开启记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void startAlarm() throws ServiceException;

    /**
     * 停止处理报警信息。
     *
     * @throws ServiceException 服务异常。
     */
    void stopAlarm() throws ServiceException;

    /**
     * 消费者ID。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    enum ConsumerId {

        EVENT_ALARM("event", "alarm"),
        VALUE_ALARM("value", "alarm"),
        EVENT_HISTORY("event", "history"),
        VALUE_HISTORY("value", "history"),

        ;
        private final String type;
        private final String label;

        ConsumerId(String type, String label) {
            this.type = type;
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return "ConsumerId{" +
                    "type='" + type + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    /**
     * 消费者状态。
     *
     * @author DwArFeng
     * @since 1.4.0
     */
    class ConsumerStatus implements Bean {

        private static final long serialVersionUID = -1494059472278157194L;

        private int bufferedSize;
        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;
        private int thread;
        private boolean idle;

        public ConsumerStatus() {
        }

        public ConsumerStatus(
                int bufferedSize, int bufferSize, int batchSize, long maxIdleTime, int thread, boolean idle) {
            this.bufferedSize = bufferedSize;
            this.bufferSize = bufferSize;
            this.batchSize = batchSize;
            this.maxIdleTime = maxIdleTime;
            this.thread = thread;
            this.idle = idle;
        }

        public int getBufferedSize() {
            return bufferedSize;
        }

        public void setBufferedSize(int bufferedSize) {
            this.bufferedSize = bufferedSize;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public long getMaxIdleTime() {
            return maxIdleTime;
        }

        public void setMaxIdleTime(long maxIdleTime) {
            this.maxIdleTime = maxIdleTime;
        }

        public int getThread() {
            return thread;
        }

        public void setThread(int thread) {
            this.thread = thread;
        }

        public boolean isIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }

        @Override
        public String toString() {
            return "ConsumerStatus{" +
                    "bufferedSize=" + bufferedSize +
                    ", bufferSize=" + bufferSize +
                    ", batchSize=" + batchSize +
                    ", maxIdleTime=" + maxIdleTime +
                    ", thread=" + thread +
                    ", idle=" + idle +
                    '}';
        }
    }
}
