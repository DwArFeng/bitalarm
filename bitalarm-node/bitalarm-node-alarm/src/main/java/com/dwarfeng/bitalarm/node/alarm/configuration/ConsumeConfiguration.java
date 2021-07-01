package com.dwarfeng.bitalarm.node.alarm.configuration;

import com.dwarfeng.bitalarm.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmHistoryValueConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmUpdatedEventConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.HistoryRecordEventConsumer;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.bitalarm.stack.handler.ConsumeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;

@Configuration
public class ConsumeConfiguration {

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    @Value("${consume.threshold.warn}")
    private double warnThreshold;

    @Autowired
    private AlarmUpdatedEventConsumer alarmUpdatedEventConsumer;
    @Value("${consume.alarm_updated_event.consumer_thread}")
    private int alarmUpdatedEventConsumerThread;
    @Value("${consume.alarm_updated_event.buffer_size}")
    private int alarmUpdatedEventBufferSize;
    @Value("${consume.alarm_updated_event.batch_size}")
    private int alarmUpdatedEventBatchSize;
    @Value("${consume.alarm_updated_event.max_idle_time}")
    private long alarmUpdatedEventMaxIdleTime;

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

    @Autowired
    private HistoryRecordEventConsumer historyRecordEventConsumer;
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

    @Autowired
    private AlarmHistoryValueConsumer alarmHistoryValueConsumer;
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
