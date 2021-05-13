package com.dwarfeng.bitalarm.node.maintain.configuration;

import com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmHistory;
import com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmSetting;
import com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmTypeIndicator;
import com.dwarfeng.bitalarm.impl.bean.entity.HibernatePoint;
import com.dwarfeng.bitalarm.impl.dao.preset.*;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmInfo;
import com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonCurrentAlarm;
import com.dwarfeng.bitalarm.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateByteIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.ByteIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Autowired
    private AlarmSettingPresetCriteriaMaker alarmSettingPresetCriteriaMaker;
    @Autowired
    private AlarmHistoryPresetCriteriaMaker alarmHistoryPresetCriteriaMaker;
    @Autowired
    private PointPresetCriteriaMaker pointPresetCriteriaMaker;

    @Autowired
    private CurrentAlarmPresetEntityFilter currentAlarmPresetEntityFilter;
    @Autowired
    private AlarmInfoPresetEntityFilter alarmInfoPresetEntityFilter;


    @Value("${redis.dbkey.current_alarm}")
    private String currentAlarmDbKey;
    @Value("${redis.dbkey.alarm_info}")
    private String alarmInfoDbKey;

    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, AlarmSetting, HibernateAlarmSetting> alarmSettingHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(AlarmSetting.class, HibernateAlarmSetting.class, mapper),
                HibernateAlarmSetting.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AlarmSetting, HibernateAlarmSetting> alarmSettingHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmSetting.class, HibernateAlarmSetting.class, mapper),
                HibernateAlarmSetting.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AlarmSetting, HibernateAlarmSetting> alarmSettingHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmSetting.class, HibernateAlarmSetting.class, mapper),
                HibernateAlarmSetting.class,
                alarmSettingPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, AlarmHistory, HibernateAlarmHistory> alarmHistoryHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(AlarmHistory.class, HibernateAlarmHistory.class, mapper),
                HibernateAlarmHistory.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AlarmHistory, HibernateAlarmHistory> alarmHistoryHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmHistory.class, HibernateAlarmHistory.class, mapper),
                HibernateAlarmHistory.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AlarmHistory, HibernateAlarmHistory> alarmHistoryHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmHistory.class, HibernateAlarmHistory.class, mapper),
                HibernateAlarmHistory.class,
                alarmHistoryPresetCriteriaMaker
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseDao<LongIdKey, CurrentAlarm, FastJsonCurrentAlarm> currentAlarmRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, FastJsonCurrentAlarm>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(CurrentAlarm.class, FastJsonCurrentAlarm.class, mapper),
                currentAlarmDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisEntireLookupDao<LongIdKey, CurrentAlarm, FastJsonCurrentAlarm> currentAlarmRedisEntireLookupDao() {
        return new RedisEntireLookupDao<>(
                (RedisTemplate<String, FastJsonCurrentAlarm>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(CurrentAlarm.class, FastJsonCurrentAlarm.class, mapper),
                currentAlarmDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisPresetLookupDao<LongIdKey, CurrentAlarm, FastJsonCurrentAlarm> currentAlarmRedisPresetLookupDao() {
        return new RedisPresetLookupDao<>(
                (RedisTemplate<String, FastJsonCurrentAlarm>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(CurrentAlarm.class, FastJsonCurrentAlarm.class, mapper),
                currentAlarmPresetEntityFilter,
                currentAlarmDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseDao<LongIdKey, AlarmInfo, FastJsonAlarmInfo> alarmInfoRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, FastJsonAlarmInfo>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(AlarmInfo.class, FastJsonAlarmInfo.class, mapper),
                alarmInfoDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisEntireLookupDao<LongIdKey, AlarmInfo, FastJsonAlarmInfo> alarmInfoRedisEntireLookupDao() {
        return new RedisEntireLookupDao<>(
                (RedisTemplate<String, FastJsonAlarmInfo>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(AlarmInfo.class, FastJsonAlarmInfo.class, mapper),
                alarmInfoDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisPresetLookupDao<LongIdKey, AlarmInfo, FastJsonAlarmInfo> alarmInfoRedisPresetLookupDao() {
        return new RedisPresetLookupDao<>(
                (RedisTemplate<String, FastJsonAlarmInfo>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(AlarmInfo.class, FastJsonAlarmInfo.class, mapper),
                alarmInfoPresetEntityFilter,
                alarmInfoDbKey
        );
    }

    @Bean
    public HibernateBatchBaseDao<ByteIdKey, HibernateByteIdKey, AlarmTypeIndicator, HibernateAlarmTypeIndicator>
    alarmTypeIndicatorHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(ByteIdKey.class, HibernateByteIdKey.class, mapper),
                new DozerBeanTransformer<>(AlarmTypeIndicator.class, HibernateAlarmTypeIndicator.class, mapper),
                HibernateAlarmTypeIndicator.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AlarmTypeIndicator, HibernateAlarmTypeIndicator> alarmTypeIndicatorHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmTypeIndicator.class, HibernateAlarmTypeIndicator.class, mapper),
                HibernateAlarmTypeIndicator.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Point, HibernatePoint> pointHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Point, HibernatePoint> pointHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Point, HibernatePoint> pointHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class,
                pointPresetCriteriaMaker
        );
    }
}
