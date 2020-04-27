package com.dwarfeng.bitalarm.impl.configuration;

import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmHistory;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmSetting;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmTypeIndicator;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.ByteIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.ByteIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    @Autowired
    private RedisTemplate<String, ?> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.prefix.entity.alarm_setting}")
    private String alarmSettingPrefix;
    @Value("${cache.prefix.entity.alarm_history}")
    private String alarmHistoryPrefix;
    @Value("${cache.prefix.list.enabled_alarm_setting}")
    private String enabledAlarmSettingPrefix;
    @Value("${cache.prefix.entity.alarm_type_indicator}")
    private String alarmTypeIndicatorPrefix;

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, AlarmSetting, FastJsonAlarmSetting> alarmSettingRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAlarmSetting>) template,
                new LongIdStringKeyFormatter(alarmSettingPrefix),
                new DozerBeanTransformer<>(AlarmSetting.class, FastJsonAlarmSetting.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, AlarmHistory, FastJsonAlarmHistory> alarmHistoryRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAlarmHistory>) template,
                new LongIdStringKeyFormatter(alarmHistoryPrefix),
                new DozerBeanTransformer<>(AlarmHistory.class, FastJsonAlarmHistory.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, AlarmSetting, FastJsonAlarmSetting> alarmSettingEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonAlarmSetting>) template,
                new LongIdStringKeyFormatter(enabledAlarmSettingPrefix),
                new DozerBeanTransformer<>(AlarmSetting.class, FastJsonAlarmSetting.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<ByteIdKey, AlarmTypeIndicator, FastJsonAlarmTypeIndicator> alarmTypeIndicatorRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAlarmTypeIndicator>) template,
                new ByteIdStringKeyFormatter(alarmTypeIndicatorPrefix),
                new DozerBeanTransformer<>(AlarmTypeIndicator.class, FastJsonAlarmTypeIndicator.class, mapper)
        );
    }
}
