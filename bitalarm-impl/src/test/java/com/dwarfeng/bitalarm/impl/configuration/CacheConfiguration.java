package com.dwarfeng.bitalarm.impl.configuration;

import com.dwarfeng.bitalarm.sdk.bean.FastJsonMapper;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmHistory;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmSetting;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmTypeIndicator;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonPoint;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator;
import com.dwarfeng.bitalarm.stack.bean.entity.Point;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${cache.prefix.entity.alarm_setting}")
    private String alarmSettingPrefix;
    @Value("${cache.prefix.entity.alarm_history}")
    private String alarmHistoryPrefix;
    @Value("${cache.prefix.list.enabled_alarm_setting}")
    private String enabledAlarmSettingPrefix;
    @Value("${cache.prefix.entity.alarm_type_indicator}")
    private String alarmTypeIndicatorPrefix;
    @Value("${cache.prefix.entity.point}")
    private String pointPrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, AlarmSetting, FastJsonAlarmSetting> alarmSettingRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAlarmSetting>) template,
                new LongIdStringKeyFormatter(alarmSettingPrefix),
                new MapStructBeanTransformer<>(AlarmSetting.class, FastJsonAlarmSetting.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, AlarmHistory, FastJsonAlarmHistory> alarmHistoryRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAlarmHistory>) template,
                new LongIdStringKeyFormatter(alarmHistoryPrefix),
                new MapStructBeanTransformer<>(AlarmHistory.class, FastJsonAlarmHistory.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, AlarmSetting, FastJsonAlarmSetting> alarmSettingEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonAlarmSetting>) template,
                new LongIdStringKeyFormatter(enabledAlarmSettingPrefix),
                new MapStructBeanTransformer<>(AlarmSetting.class, FastJsonAlarmSetting.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, AlarmTypeIndicator, FastJsonAlarmTypeIndicator>
    alarmTypeIndicatorRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAlarmTypeIndicator>) template,
                new StringIdStringKeyFormatter(alarmTypeIndicatorPrefix),
                new MapStructBeanTransformer<>(
                        AlarmTypeIndicator.class, FastJsonAlarmTypeIndicator.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Point, FastJsonPoint> pointRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPoint>) template,
                new LongIdStringKeyFormatter(pointPrefix),
                new MapStructBeanTransformer<>(Point.class, FastJsonPoint.class, FastJsonMapper.class)
        );
    }
}
