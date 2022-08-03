package com.dwarfeng.bitalarm.node.maintain.configuration;

import com.dwarfeng.bitalarm.impl.service.operation.*;
import com.dwarfeng.bitalarm.stack.bean.entity.*;
import com.dwarfeng.bitalarm.stack.cache.AlarmTypeIndicatorCache;
import com.dwarfeng.bitalarm.stack.dao.*;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.*;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    @Bean
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Autowired
    private AlarmSettingCrudOperation alarmSettingCrudOperation;
    @Autowired
    private AlarmSettingDao alarmSettingDao;
    @Autowired
    private AlarmHistoryCrudOperation alarmHistoryCrudOperation;
    @Autowired
    private AlarmHistoryDao alarmHistoryDao;
    @Autowired
    private CurrentAlarmCrudOperation currentAlarmCrudOperation;
    @Autowired
    private CurrentAlarmDao currentAlarmDao;
    @Autowired
    private AlarmInfoCrudOperation alarmInfoCrudOperation;
    @Autowired
    private AlarmInfoDao alarmInfoDao;
    @Autowired
    private AlarmTypeIndicatorDao alarmTypeIndicatorDao;
    @Autowired
    private AlarmTypeIndicatorCache alarmTypeIndicatorCache;
    @Autowired
    private PointCrudOperation pointCrudOperation;
    @Autowired
    private PointDao pointDao;

    @Value("${cache.timeout.entity.alarm_type_indicator}")
    private long alarmTypeIndicatorTimeout;

    @Bean
    public CustomBatchCrudService<LongIdKey, AlarmSetting> alarmSettingCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                alarmSettingCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmSetting> alarmSettingDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                alarmSettingDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmSetting> alarmSettingDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                alarmSettingDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, AlarmHistory> alarmHistoryCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                alarmHistoryCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmHistory> alarmHistoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                alarmHistoryDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmHistory> alarmHistoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                alarmHistoryDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, CurrentAlarm> currentAlarmCustomCrudService() {
        return new CustomCrudService<>(
                currentAlarmCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CurrentAlarm> currentAlarmDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                currentAlarmDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<CurrentAlarm> currentAlarmDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                currentAlarmDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, AlarmInfo> alarmInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                alarmInfoCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmInfo> alarmInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                alarmInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmInfo> alarmInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                alarmInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, AlarmTypeIndicator> alarmTypeIndicatorGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                alarmTypeIndicatorDao,
                alarmTypeIndicatorCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmTypeIndicatorTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmTypeIndicator> alarmTypeIndicatorDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                alarmTypeIndicatorDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Point> pointBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                pointCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Point> pointDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                pointDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Point> pointDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                pointDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
