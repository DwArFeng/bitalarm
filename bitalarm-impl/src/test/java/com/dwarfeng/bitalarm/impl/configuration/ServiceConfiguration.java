package com.dwarfeng.bitalarm.impl.configuration;

import com.dwarfeng.bitalarm.impl.service.operation.*;
import com.dwarfeng.bitalarm.stack.bean.entity.*;
import com.dwarfeng.bitalarm.stack.cache.AlarmTypeIndicatorCache;
import com.dwarfeng.bitalarm.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;

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
            GenerateConfiguration generateConfiguration,
            AlarmSettingCrudOperation alarmSettingCrudOperation,
            AlarmSettingDao alarmSettingDao,
            AlarmHistoryCrudOperation alarmHistoryCrudOperation,
            PointCrudOperation pointCrudOperation,
            AlarmHistoryDao alarmHistoryDao,
            CurrentAlarmCrudOperation currentAlarmCrudOperation,
            CurrentAlarmDao currentAlarmDao,
            PointDao pointDao,
            AlarmInfoCrudOperation alarmInfoCrudOperation,
            AlarmInfoDao alarmInfoDao,
            AlarmTypeIndicatorDao alarmTypeIndicatorDao,
            AlarmTypeIndicatorCache alarmTypeIndicatorCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
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
    public CustomBatchCrudService<LongIdKey, AlarmSetting> alarmSettingCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmSettingCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmSetting> alarmSettingDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmSettingDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmSetting> alarmSettingDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmSettingDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, AlarmHistory> alarmHistoryCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmHistoryCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmHistory> alarmHistoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmHistoryDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmHistory> alarmHistoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmHistoryDao
        );
    }

    @Bean
    public CustomCrudService<LongIdKey, CurrentAlarm> currentAlarmCustomCrudService() {
        return new CustomCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                currentAlarmCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CurrentAlarm> currentAlarmDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                currentAlarmDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<CurrentAlarm> currentAlarmDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                currentAlarmDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, AlarmInfo> alarmInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmInfoCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmInfo> alarmInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmInfo> alarmInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, AlarmTypeIndicator> alarmTypeIndicatorGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmTypeIndicatorDao,
                alarmTypeIndicatorCache,
                new ExceptionKeyGenerator<>(),
                alarmTypeIndicatorTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmTypeIndicator> alarmTypeIndicatorDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                alarmTypeIndicatorDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Point> pointBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pointCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Point> pointDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pointDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Point> pointDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pointDao
        );
    }
}
