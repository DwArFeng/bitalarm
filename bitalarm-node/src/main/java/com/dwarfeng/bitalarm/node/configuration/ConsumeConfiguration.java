package com.dwarfeng.bitalarm.node.configuration;

import com.dwarfeng.bitalarm.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmHistoryValueConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmUpdatedEventConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.HistoryRecordEventConsumer;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.handler.ConsumeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;

@Configuration
public class ConsumeConfiguration {

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    private final AlarmUpdatedEventConsumer alarmUpdatedEventConsumer;
    private final HistoryRecordEventConsumer historyRecordEventConsumer;
    private final AlarmHistoryValueConsumer alarmHistoryValueConsumer;

    @Value("${consume.threshold.warn}")
    private double warnThreshold;

    @Value("${consume.alarm_updated_event.consumer_thread}")
    private int alarmUpdatedEventConsumerThread;
    @Value("${consume.alarm_updated_event.buffer_size}")
    private int alarmUpdatedEventBufferSize;
    @Value("${consume.alarm_updated_event.batch_size}")
    private int alarmUpdatedEventBatchSize;
    @Value("${consume.alarm_updated_event.max_idle_time}")
    private long alarmUpdatedEventMaxIdleTime;

    public ConsumeConfiguration(
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler,
            AlarmUpdatedEventConsumer alarmUpdatedEventConsumer,
            HistoryRecordEventConsumer historyRecordEventConsumer,
            AlarmHistoryValueConsumer alarmHistoryValueConsumer
    ) {
        this.executor = executor;
        this.scheduler = scheduler;
        this.alarmUpdatedEventConsumer = alarmUpdatedEventConsumer;
        this.historyRecordEventConsumer = historyRecordEventConsumer;
        this.alarmHistoryValueConsumer = alarmHistoryValueConsumer;
    }

    @Bean("alarmUpdatedEventConsumeHandler")
    public ConsumeHandler<AlarmInfo> alarmUpdatedEventConsumeHandler() {
        ConsumeHandlerImpl<AlarmInfo> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmUpdatedEventConsumer,
                alarmUpdatedEventConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(alarmUpdatedEventBufferSize, alarmUpdatedEventBatchSize,
                alarmUpdatedEventMaxIdleTime);
        return consumeHandler;
    }

    @Value("${consume.history_recorded_event.consumer_thread}")
    private int historyRecordedEventConsumerThread;
    @Value("${consume.history_recorded_event.buffer_size}")
    private int historyRecordedEventBufferSize;
    @Value("${consume.history_recorded_event.batch_size}")
    private int historyRecordedEventBatchSize;
    @Value("${consume.history_recorded_event.max_idle_time}")
    private long historyRecordedEventMaxIdleTime;

    @Bean("historyRecordedEventConsumeHandler")
    public ConsumeHandler<AlarmHistory> historyRecordedEventConsumeHandler() {
        ConsumeHandlerImpl<AlarmHistory> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                historyRecordEventConsumer,
                historyRecordedEventConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(historyRecordedEventBufferSize, historyRecordedEventBatchSize,
                historyRecordedEventMaxIdleTime);
        return consumeHandler;
    }

    @Value("${consume.alarm_history_value.consumer_thread}")
    private int alarmHistoryValueConsumerThread;
    @Value("${consume.alarm_history_value.buffer_size}")
    private int alarmHistoryValueBufferSize;
    @Value("${consume.alarm_history_value.batch_size}")
    private int alarmHistoryValueBatchSize;
    @Value("${consume.alarm_history_value.max_idle_time}")
    private long alarmHistoryValueMaxIdleTime;

    @Bean("alarmHistoryValueConsumeHandler")
    public ConsumeHandler<AlarmHistory> alarmHistoryValueConsumeHandler() {
        ConsumeHandlerImpl<AlarmHistory> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmHistoryValueConsumer,
                alarmHistoryValueConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(alarmHistoryValueBufferSize, alarmHistoryValueBatchSize,
                alarmHistoryValueMaxIdleTime);
        return consumeHandler;
    }
}
