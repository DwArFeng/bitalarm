package com.dwarfeng.bitalarm.impl.configuration;

import com.dwarfeng.bitalarm.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmAppearEventConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmDisappearEventConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.AlarmHistoryValueConsumer;
import com.dwarfeng.bitalarm.impl.handler.consumer.HistoryRecordEventConsumer;
import com.dwarfeng.bitalarm.stack.bean.dto.AlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
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
    private AlarmAppearEventConsumer alarmAppearedEventConsumer;
    @Value("${consume.alarm_appeared_event.consumer_thread}")
    private int alarmAppearedEventConsumerThread;
    @Value("${consume.alarm_appeared_event.buffer_size}")
    private int alarmAppearedEventBufferSize;
    @Value("${consume.alarm_appeared_event.batch_size}")
    private int alarmAppearedEventBatchSize;
    @Value("${consume.alarm_appeared_event.max_idle_time}")
    private long alarmAppearedEventMaxIdleTime;

    @Bean("alarmAppearEventConsumerHandler")
    public ConsumeHandler<AlarmInfo> alarmAppearEventConsumerHandler() {
        ConsumeHandlerImpl<AlarmInfo> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmAppearedEventConsumer,
                alarmAppearedEventConsumerThread
        );
        consumeHandler.setBufferParameters(alarmAppearedEventBufferSize, alarmAppearedEventBatchSize,
                alarmAppearedEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private AlarmDisappearEventConsumer alarmDisappearEventConsumer;
    @Value("${consume.alarm_disappeared_event.consumer_thread}")
    private int alarmDisappearedEventConsumerThread;
    @Value("${consume.alarm_disappeared_event.buffer_size}")
    private int alarmDisappearedEventBufferSize;
    @Value("${consume.alarm_disappeared_event.batch_size}")
    private int alarmDisappearedEventBatchSize;
    @Value("${consume.alarm_disappeared_event.max_idle_time}")
    private long alarmDisappearedEventMaxIdleTime;

    @Bean("alarmDisappearEventConsumerHandler")
    public ConsumeHandler<AlarmInfo> alarmDisappearEventConsumerHandler() {
        ConsumeHandlerImpl<AlarmInfo> consumeHandler = new ConsumeHandlerImpl<>(
                threadPoolTaskExecutor,
                new ArrayList<>(),
                new ArrayList<>(),
                alarmDisappearEventConsumer,
                alarmDisappearedEventConsumerThread
        );
        consumeHandler.setBufferParameters(alarmDisappearedEventBufferSize, alarmDisappearedEventBatchSize,
                alarmDisappearedEventMaxIdleTime);
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

    @Bean
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
