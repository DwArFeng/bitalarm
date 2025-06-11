package com.dwarfeng.bitalarm.impl.handler.source;

import com.dwarfeng.bitalarm.sdk.handler.source.AbstractSource;
import com.dwarfeng.bitalarm.stack.exception.AlarmDisabledException;
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 接收 Dcti 标准采集数据的 Kafka 数据源。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DctiKafkaSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctiKafkaSource.class);

    private final KafkaListenerEndpointRegistry registry;

    @Value("${source.kafka.dcti.listener_id}")
    private String listenerId;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DctiKafkaSource(KafkaListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void doOnline() throws Exception {
        LOGGER.info("Kafka source 上线...");
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (Objects.isNull(listenerContainer)) {
            throw new HandlerException("找不到 kafka listener container " + listenerId);
        }
        //判断监听容器是否启动，未启动则将其启动
        if (!listenerContainer.isRunning()) {
            listenerContainer.start();
        }
        listenerContainer.resume();
    }

    @Override
    protected void doOffline() throws Exception {
        LOGGER.info("Kafka source 下线...");
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (Objects.isNull(listenerContainer)) {
            throw new HandlerException("找不到 kafka listener container " + listenerId);
        }
        listenerContainer.pause();
    }

    @KafkaListener(
            id = "${source.kafka.dcti.listener_id}",
            containerFactory = "dctiKafkaSource.kafkaListenerContainerFactory",
            topics = "${source.kafka.dcti.listener_topic}"
    )
    public void handleDataInfo(
            List<ConsumerRecord<String, String>> consumerRecords, Consumer<String, String> consumer, Acknowledgment ack
    ) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String message = consumerRecord.value();
            try {
                DataInfo dataInfo = DataInfoUtil.fromMessage(message);

                // 如果从消息队列里接到了 null 值的标识，则忽略本次数据。
                if (StringUtils.equals(dataInfo.getValue(), "!null")) {
                    break;
                }

                long pointId = dataInfo.getPointLongId();
                LongIdKey pointKey = new LongIdKey(pointId);
                byte[] data = null;
                Date happenedDate = dataInfo.getHappenedDate();
                if (StringUtils.equalsIgnoreCase("T", dataInfo.getValue())
                        || StringUtils.equalsIgnoreCase("F", dataInfo.getValue())) {
                    if (StringUtils.equalsIgnoreCase("T", dataInfo.getValue())) {
                        data = new byte[]{(byte) 1};
                    } else {
                        data = new byte[]{(byte) 0};
                    }
                } else {
                    Base64.getDecoder().decode(dataInfo.getValue());
                }
                context.processAlarm(pointKey, data, happenedDate);
            } catch (AlarmDisabledException e) {
                LOGGER.warn("记录处理器被禁用， 消息 " + message + " 以及其后同一批次的消息均不会被提交", e);
                // 如果记录处理器被禁用，则放弃其后同一批次的消息记录，并且妥善处理offset的提交。
                // Offset 精确设置到没有提交成功的最后一条信息上。
                consumer.seek(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()),
                        consumerRecord.offset());
                ack.acknowledge();
                return;
            } catch (Exception e) {
                LOGGER.warn("记录处理器无法处理, 消息 " + message + " 将会被忽略", e);
            }
        }
        ack.acknowledge();
    }

    @Configuration
    @EnableKafka
    public static class KafkaSourceConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSourceConfiguration.class);

        @Value("${source.kafka.dcti.bootstrap_servers}")
        private String consumerBootstrapServers;
        @Value("${source.kafka.dcti.session_timeout_ms}")
        private int sessionTimeoutMs;
        @Value("${source.kafka.dcti.auto_offset_reset}")
        private String autoOffsetReset;
        @Value("${source.kafka.dcti.concurrency}")
        private int concurrency;
        @Value("${source.kafka.dcti.poll_timeout}")
        private int pollTimeout;
        @Value("${source.kafka.dcti.max_poll_records}")
        private int maxPollRecords;
        @Value("${source.kafka.dcti.max_poll_interval_ms}")
        private int maxPollIntervalMs;

        @Bean("dctiKafkaSource.consumerProperties")
        public Map<String, Object> consumerProperties() {
            LOGGER.info("配置Kafka消费者属性...");
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
            // 本实例使用ack手动提交，因此禁止自动提交的功能。
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
            LOGGER.debug("Kafka消费者属性配置完成...");
            return props;
        }

        @Bean("dctiKafkaSource.consumerFactory")
        public ConsumerFactory<String, String> consumerFactory() {
            LOGGER.info("配置Kafka消费者工厂...");
            Map<String, Object> properties = consumerProperties();
            DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(properties);
            factory.setKeyDeserializer(new StringDeserializer());
            factory.setValueDeserializer(new StringDeserializer());
            LOGGER.debug("Kafka消费者工厂配置完成");
            return factory;
        }

        @Bean("dctiKafkaSource.kafkaListenerContainerFactory")
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
            LOGGER.info("配置Kafka侦听容器工厂...");
            ConsumerFactory<String, String> consumerFactory = consumerFactory();
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory);
            factory.setConcurrency(concurrency);
            factory.getContainerProperties().setPollTimeout(pollTimeout);
            // Kafka侦听容器通过框架对开启和关闭进行托管，因此在启动时不自动开启。
            factory.setAutoStartup(false);
            // 监听器启用批量监听模式。
            factory.setBatchListener(true);
            // 配置ACK模式为手动立即提交。
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
            LOGGER.info("配置Kafka侦听容器工厂...");
            return factory;
        }
    }
}
