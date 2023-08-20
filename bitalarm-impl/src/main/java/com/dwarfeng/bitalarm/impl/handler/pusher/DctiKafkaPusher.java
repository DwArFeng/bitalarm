package com.dwarfeng.bitalarm.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmHistory;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmInfo;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo;
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标准数据接口Kafka推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DctiKafkaPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "dcti.kafka";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${pusher.dcti.kafka.topic.alarm_updated}")
    private String alarmUpdatedTopic;
    @Value("${pusher.dcti.kafka.topic.history_recorded}")
    private String historyRecordedTopic;
    @Value("${pusher.dcti.kafka.topic.alarm_reset}")
    private String alarmResetTopic;

    public DctiKafkaPusher(
            @Qualifier("dctiKafkaPusher.kafkaTemplate")
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        super(PUSHER_TYPE);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void alarmUpdated(AlarmInfo alarmInfo) {
        String value = JSON.toJSONString(FastJsonAlarmInfo.of(alarmInfo));
        DataInfo dataInfo = new DataInfo(
                alarmInfo.getKey().getLongId(), value, alarmInfo.getHappenedDate());
        kafkaTemplate.send(alarmUpdatedTopic, DataInfoUtil.toMessage(dataInfo));
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void alarmUpdated(List<AlarmInfo> alarmInfos) {
        alarmInfos.forEach(this::alarmUpdated);
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void historyRecorded(AlarmHistory alarmHistory) {
        String value = JSON.toJSONString(FastJsonAlarmHistory.of(alarmHistory));
        DataInfo dataInfo = new DataInfo(
                alarmHistory.getAlarmSettingKey().getLongId(), value, alarmHistory.getEndDate());
        kafkaTemplate.send(historyRecordedTopic, DataInfoUtil.toMessage(dataInfo));
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void historyRecorded(List<AlarmHistory> alarmHistories) {
        alarmHistories.forEach(this::historyRecorded);
    }

    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    @Override
    public void alarmReset() {
        kafkaTemplate.send(alarmResetTopic, StringUtils.EMPTY);
    }

    @Configuration
    public static class KafkaPusherConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPusherConfiguration.class);

        @Value("${pusher.dcti.kafka.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${pusher.dcti.kafka.retries}")
        private int retries;
        @Value("${pusher.dcti.kafka.linger}")
        private long linger;
        @Value("${pusher.dcti.kafka.buffer_memory}")
        private long bufferMemory;
        @Value("${pusher.dcti.kafka.batch_size}")
        private int batchSize;
        @Value("${pusher.dcti.kafka.acks}")
        private String acks;
        @Value("${pusher.dcti.kafka.transaction_prefix}")
        private String transactionPrefix;

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaPusher.producerProperties")
        public Map<String, Object> producerProperties() {
            LOGGER.info("配置Kafka生产者属性...");
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            props.put(ProducerConfig.RETRIES_CONFIG, retries);
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            props.put(ProducerConfig.ACKS_CONFIG, acks);
            LOGGER.debug("Kafka生产者属性配置完成...");
            return props;
        }

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaPusher.producerFactory")
        public ProducerFactory<String, String> producerFactory() {
            LOGGER.info("配置Kafka生产者工厂...");
            Map<String, Object> properties = producerProperties();
            DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(properties);
            factory.setTransactionIdPrefix(transactionPrefix);
            factory.setKeySerializer(new StringSerializer());
            factory.setValueSerializer(new StringSerializer());
            LOGGER.debug("Kafka生产者工厂配置完成");
            return factory;
        }

        @Bean("dctiKafkaPusher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            LOGGER.info("生成KafkaTemplate...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
            LOGGER.debug("KafkaTemplate生成完成...");
            return kafkaTemplate;
        }

        @Bean("dctiKafkaPusher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            LOGGER.info("生成KafkaTransactionManager...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            LOGGER.debug("KafkaTransactionManager生成完成...");
            return new KafkaTransactionManager<>(producerFactory);
        }
    }
}
