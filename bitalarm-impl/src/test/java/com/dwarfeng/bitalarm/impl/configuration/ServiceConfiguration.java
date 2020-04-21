package com.dwarfeng.bitalarm.impl.configuration;

import com.dwarfeng.bitalarm.impl.service.operation.AlarmHistoryCrudOperation;
import com.dwarfeng.bitalarm.impl.service.operation.AlarmSettingCrudOperation;
import com.dwarfeng.bitalarm.impl.service.operation.CurrentAlarmCrudOperation;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory;
import com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting;
import com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm;
import com.dwarfeng.bitalarm.stack.dao.AlarmHistoryDao;
import com.dwarfeng.bitalarm.stack.dao.AlarmSettingDao;
import com.dwarfeng.bitalarm.stack.dao.CurrentAlarmDao;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.CustomCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CurrentAlarmDao currentAlarmDao;
    @Autowired
    private CurrentAlarmCrudOperation currentAlarmCrudOperation;

    @Bean
    public CustomCrudService<LongIdKey, AlarmSetting> alarmSettingCustomCrudService() {
        return new CustomCrudService<>(
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
}
