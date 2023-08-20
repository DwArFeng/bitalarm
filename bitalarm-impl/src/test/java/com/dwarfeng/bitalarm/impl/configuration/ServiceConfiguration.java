package com.dwarfeng.bitalarm.impl.configuration;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final AlarmSettingCrudOperation alarmSettingCrudOperation;
    private final AlarmSettingDao alarmSettingDao;
    private final AlarmHistoryCrudOperation alarmHistoryCrudOperation;
    private final AlarmHistoryDao alarmHistoryDao;
    private final CurrentAlarmCrudOperation currentAlarmCrudOperation;
    private final CurrentAlarmDao currentAlarmDao;
    private final AlarmInfoCrudOperation alarmInfoCrudOperation;
    private final AlarmInfoDao alarmInfoDao;
    private final AlarmTypeIndicatorDao alarmTypeIndicatorDao;
    private final AlarmTypeIndicatorCache alarmTypeIndicatorCache;
    private final PointCrudOperation pointCrudOperation;
    private final PointDao pointDao;

    @Value("${cache.timeout.entity.alarm_type_indicator}")
    private long alarmTypeIndicatorTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            AlarmSettingCrudOperation alarmSettingCrudOperation, AlarmSettingDao alarmSettingDao,
            AlarmHistoryCrudOperation alarmHistoryCrudOperation, PointCrudOperation pointCrudOperation,
            AlarmHistoryDao alarmHistoryDao, CurrentAlarmCrudOperation currentAlarmCrudOperation,
            CurrentAlarmDao currentAlarmDao,
            PointDao pointDao,
            AlarmInfoCrudOperation alarmInfoCrudOperation, AlarmInfoDao alarmInfoDao,
            AlarmTypeIndicatorDao alarmTypeIndicatorDao, AlarmTypeIndicatorCache alarmTypeIndicatorCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.alarmSettingCrudOperation = alarmSettingCrudOperation;
        this.alarmSettingDao = alarmSettingDao;
        this.alarmHistoryCrudOperation = alarmHistoryCrudOperation;
        this.pointCrudOperation = pointCrudOperation;
        this.alarmHistoryDao = alarmHistoryDao;
        this.currentAlarmCrudOperation = currentAlarmCrudOperation;
        this.currentAlarmDao = currentAlarmDao;
        this.pointDao = pointDao;
        this.alarmInfoCrudOperation = alarmInfoCrudOperation;
        this.alarmInfoDao = alarmInfoDao;
        this.alarmTypeIndicatorDao = alarmTypeIndicatorDao;
        this.alarmTypeIndicatorCache = alarmTypeIndicatorCache;
    }

    @Bean
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

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
