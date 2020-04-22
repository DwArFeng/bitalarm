package com.dwarfeng.bitalarm.node.alarm.configuration;

import com.dwarfeng.bitalarm.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmHistoryValueConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmInfoValueConsumer;
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

import java.util.ArrayList;

@Configuration
public class ConsumeConfiguration {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmUpdatedEventConsumer,
                alarmUpdatedEventConsumerThread
        );
        consumeHandler.setBufferParameters(alarmUpdatedEventBufferSize, alarmUpdatedEventBatchSize,
                alarmUpdatedEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private AlarmInfoValueConsumer alarmInfoValueConsumer;
    @Value("${consume.alarm_info_value.consumer_thread}")
    private int alarmInfoValueConsumerThread;
    @Value("${consume.alarm_info_value.buffer_size}")
    private int alarmInfoValueBufferSize;
    @Value("${consume.alarm_info_value.batch_size}")
    private int alarmInfoValueBatchSize;
    @Value("${consume.alarm_info_value.max_idle_time}")
    private long alarmInfoValueMaxIdleTime;

    @Bean("alarmInfoValueConsumeHandler")
    public ConsumeHandler<AlarmInfo> alarmInfoValueConsumeHandler() {
        ConsumeHandlerImpl<AlarmInfo> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmInfoValueConsumer,
                alarmInfoValueConsumerThread
        );
        consumeHandler.setBufferParameters(alarmInfoValueBufferSize, alarmInfoValueBatchSize,
                alarmInfoValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private AlarmHistoryValueConsumer alarmHistoryValueConsumer;
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
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmHistoryValueConsumer,
                historyRecordedEventConsumerThread
        );
        consumeHandler.setBufferParameters(historyRecordedEventBufferSize, historyRecordedEventBatchSize,
                historyRecordedEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private HistoryRecordEventConsumer historyRecordEventConsumer;
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
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                historyRecordEventConsumer,
                alarmHistoryValueConsumerThread
        );
        consumeHandler.setBufferParameters(alarmHistoryValueBufferSize, alarmHistoryValueBatchSize,
                alarmHistoryValueMaxIdleTime);
        return consumeHandler;
    }
}
