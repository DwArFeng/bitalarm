# ChangeLog

### Release_1.8.0_20250610_build_A

#### 功能构建

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.log4j2.Log4j2Command。

- 导入运维指令。
  - com.dwarfeng.datamark.service.telqos.*。

- 增加 Hibernate 实体数据标记字段，并应用相关实体侦听器。
  - com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmSetting。
  - com.dwarfeng.bitalarm.impl.bean.entity.HibernateAlarmTypeIndicator。
  - com.dwarfeng.bitalarm.impl.bean.entity.HibernatePoint。

- 增加依赖。
  - 增加依赖 `dwarfeng-datamark` 以应用其新功能，版本为 `1.0.2.a`。

- 增加 `PusherAdapter`。
  - 建议任何插件的推送器实现都继承自该适配器。
  - 适配器对所有的事件推送方法都进行了空实现。
  - 解决了增加了新的事件时，旧的推送器实现必须实现新的方法的问题。
  - 从此以后，推送器增加新的事件，将被视作兼容性更新。

- SPI 目录结构优化。
  - 将驱动机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将执行机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将推送机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将重置机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。

- 优化部分类中部分方法的行为分析行为。
  - com.dwarfeng.bitalarm.impl.cache.AlarmHistoryCacheImpl。
  - com.dwarfeng.bitalarm.impl.cache.AlarmSettingCacheImpl。
  - com.dwarfeng.bitalarm.impl.cache.AlarmTypeIndicatorCacheImpl。
  - com.dwarfeng.bitalarm.impl.cache.EnabledAlarmSettingCacheImpl。
  - com.dwarfeng.bitalarm.impl.dao.AlarmHistoryDaoImpl。
  - com.dwarfeng.bitalarm.impl.dao.AlarmInfoDaoImpl。
  - com.dwarfeng.bitalarm.impl.dao.AlarmSettingDaoImpl。
  - com.dwarfeng.bitalarm.impl.dao.AlarmTypeIndicatorDaoImpl。
  - com.dwarfeng.bitalarm.impl.dao.CurrentAlarmDaoImpl。
  - com.dwarfeng.bitalarm.impl.handler.AlarmLocalCacheHandlerImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmHistoryMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmInfoMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmSettingMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmTypeIndicatorMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.CurrentAlarmMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.PointMaintainServiceImpl。

- 优化部分维护服务实现中的部分方法的性能。
  - com.dwarfeng.bitalarm.impl.service.AlarmHistoryMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmInfoMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmSettingMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.AlarmTypeIndicatorMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.CurrentAlarmMaintainServiceImpl。
  - com.dwarfeng.bitalarm.impl.service.PointMaintainServiceImpl。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.10.a` 并解决兼容性问题，以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.39` 以规避漏洞。
  - 升级 `kafka` 依赖版本为 `3.9.0` 以规避漏洞。
  - 升级 `spring-kafka` 依赖版本为 `2.9.11` 以规避漏洞。
  - 升级 `protobuf` 依赖版本为 `3.25.5` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.2.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.119.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.3` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.6.4.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.14.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.13.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.11.a` 以规避漏洞。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。
  - 优化日志配置结构，提供 `confext/logging-settings.xml` 配置文件，以供外部功能自定义日志配置。
  - 优化启动脚本，使服务支持新的日志配置结构。
  - 优化 `assembly.xml`，使项目打包时输出新的日志配置结构。
  - 优化 `confext/README.md`，添加新的日志配置结构的相关说明。

- 优化项目启停脚本设置程序的根目录的方式。

- 优化 `node` 模块部分服务启停脚本的注释。
  - binres/bitalarm-start.bat。
  - binres/bitalarm-start.sh。

- 优化启停脚本的目录结构。

- 新增预设查询。
  - com.dwarfeng.bitalarm.stack.service.AlarmHistoryMaintainService。
  - com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService。

#### Bug修复

- 修正配置类中的错误。
  - ExceptionCodeOffsetConfiguration。

- 修复当某点位一直推送为报警点时，报警时间一直更新问题。

#### 功能移除

- (无)

---

### Release_1.7.1_20230821_build_A

#### 功能构建

- 优化 `DctiKafkaSource` 的配置键的命名。
  - **这将导致配置不兼容，需要在部署时修改旧的配置。**

- 优化 `source.properties` 配置文件有关 Kafka 部分的注释。

- 优化 `DctiKafkaPusher` 的配置键的命名。
  - **这将导致配置不兼容，需要在部署时修改旧的配置。**

- 优化 `NativeKafkaPusher` 的配置键的命名。
  - **这将导致配置不兼容，需要在部署时修改旧的配置。**

- 优化 `push.properties` 配置文件有关 Kafka 部分的注释。

#### Bug修复

- 修复 `pom.xml` 中错误的配置。

#### 功能移除

- (无)

---

### Release_1.7.0_20230820_build_A

#### 功能构建

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

- 优化项目结构。
  - 为项目添加 `confext` 文件夹。

- 重构项目的报警处理机制。

- 增加重置机制，实现报警功能的重置。

- 使用 `ApplicationUtil` 重构 `Launcher`。

- Dubbo 微服务增加分组配置。

- 优化 `opt-*.xml` 文件的格式。

- 使用 `subgrade` 工具库替代本地缓存实现。

- 将 Bean 的注入形式由注解注入改为构造器注入。

- 使用 `MapStruct` 重构 `BeanTransformer`。

- 增加依赖。
  - 增加依赖 `protobuf` 以规避漏洞，版本为 `3.19.6`。
  - 增加依赖 `guava` 以规避漏洞，版本为 `31.1-jre`。
  - 增加依赖 `gson` 以规避漏洞，版本为 `2.8.9`。
  - 增加依赖 `snakeyaml` 以规避漏洞，版本为 `2.0`。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `kafka` 依赖版本为 `2.6.3` 以规避漏洞。
  - 升级 `spring-kafka` 依赖版本为 `2.8.10` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.31` 以规避漏洞。
  - 升级 `jedis` 依赖版本为 `3.8.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.5` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.5.7` 以规避漏洞。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。
  - 升级 `curator` 依赖版本为 `4.3.0` 以规避漏洞。
  - 升级 `hibernate` 依赖版本为 `5.4.24.Final` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.2.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.11.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `dwarfeng-ftp` 依赖版本为 `1.1.10.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.7.a` 以规避漏洞。

- 插件升级
  - 升级 `maven-compiler-plugin` 插件版本为 `3.1.0`。

- 重构 `node` 模块。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的推送器。
  - 删除 `PartialDrainPusher`。

- 删除不需要的依赖。
  - 删除 `dozer` 依赖。
  - 删除 `el` 依赖。
  - 删除 `zkclient` 依赖。
  - 删除 `noggit` 依赖。
  - 删除 `aopalliance` 依赖。

---

### Release_1.6.4_20220929_build_A

#### 功能构建

- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.20` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.28` 以规避漏洞。
  - 升级 `hibernate` 依赖版本为 `5.3.20.Final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.17.2` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.1.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.9.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.13.b` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.9.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.4.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.3.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的依赖。
  - 删除 `joda-time` 依赖。
  - 删除 `commons-lang3` 依赖。
  - 删除 `commons-io` 依赖。
  - 删除 `commons-beanutils` 依赖。
  - 删除 `pagehelper` 依赖。
  - 删除 `jsqlparser` 依赖。
  - 删除 `commons-fileupload` 依赖。
  - 删除 `httpcomponents` 依赖。

---

### Release_1.6.3_20220803_build_A

#### 功能构建

- 修改报警信息维护服务，增加批量操作

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.2_20220707_build_A

#### 功能构建

- 优化配置项
- 实现分页行数为负时查询全部数据的功能

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.1_20220701_build_A

#### 功能构建

- 新增历史报警预设查询

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.0_20220523_build_A

#### 功能构建

- 依赖升级。
  - 升级 `netty` 依赖版本为 `4.1.77.Final` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.0.21.Final` 以规避漏洞。
  - 升级 `hibernate` 依赖版本为 `5.3.20.Final` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.4.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.4.6.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.2.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.1.a` 以规避漏洞。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。

- 报警类型字段的数据类型由 `Byte` 改为 `String`。

- 改进部分内部配置文件的格式。

- 增加实体字段。
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator.remark。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.5_20220516_build_A

#### 功能构建

- 升级 `hibernate` 版本为 `5.4.24.Final`。

- 升级 `spring-core` 版本为 `5.3.19`。

- 升级 `mysql` 版本为 `8.0.27`。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.4_20220113_build_A

#### 功能构建

- 升级 `log4j2` 依赖版本为 `2.17.1` 以规避漏洞。
  - `CVE-2021-44228`。
  - `CVE-2021-45105`。

- 升级 `dubbo` 依赖版本为 `2.7.15`。

- 升级 `spring` 依赖版本为 `5.3.14`。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.3_20210929_build_A

#### 功能构建

- 对推送器进行抽象，形成 com.dwarfeng.bitalarm.impl.handler.pusher.AbstractPusher。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20210730_build_A

#### 功能构建

- AlarmHistoryMaintainService 增加以时间倒序的预设查询。
- 优化 AlarmHandlerImpl 中的逻辑判断代码。

#### Bug修复

- (无)

#### 功能移除

- 删除无用的项目依赖。
  - ~~spring-web~~。
  - ~~spring-mvc~~。

---

### Release_1.5.1_20210701_build_A

#### 功能构建

- 完善 DctiKafkaSource 的 null 值处理逻辑。
- 调整实体字段: com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory.pointKey。

#### Bug修复

- 修正 HistoryRecordEventConsumer 的处理逻辑中的 bug。
- 修正 ConsumeConfiguration 中的消费者配置 bug。
- 修正报警处理逻辑在处理大量数据时可能产生的 bug。

#### 功能移除

- (无)

---

### Release_1.5.0_20210513_build_A

#### 功能构建

- 优化 `DctiKafkaSource`，使其支持布尔值数据。
- 添加实体 `com.dwarfeng.bitalarm.stack.bean.entity.Point`
  - 更改旧实体以与该实体相关联。
    - com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting
    - com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo
    - com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm
  - 优化关联实体的查询方法。
- 更改实体服务，使其支持预设查询。
  - com.dwarfeng.bitalarm.stack.service.AlarmInfoMaintainService
  - com.dwarfeng.bitalarm.stack.service.CurrentAlarmMaintainService

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.3_20210203_build_A

#### 功能构建

- 去除 solrj 依赖。

#### Bug修复

- 去除 DctiKafkaSource 中可能引起程序行为异常的配置项。

#### 功能移除

- (无)

---

### Release_1.4.2_20210127_build_A

#### 功能构建

- 为 AlarmHandlerImpl.processAlarm 方法增加性能分析增强。

- 添加实体属性。
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory.pointKey。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.1_20210126_build_A

#### 功能构建

- 去除预设查询中多余的排序动作。
- 解决项目中冲突的依赖。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.0_20201127_build_A

#### 功能构建

- 升级 subgrade 依赖版本为 1.2.0.b。
- 升级 spring-terminator 依赖版本为 1.0.7.a
- dubbo 配置项优化。
- 设置 application-context-task.xml 中的参数设置为可配置。
- 优化与增强 ConsumeHandler 的性能。
  - 添加容量监视功能，缓存中的容量与最大容量之间的比例超过设定的阈值会产生报警日志。
  - 结束时输出的日志更加人性化。
  - 优化处理器在工作时的日志输出。
- AlarmQosService 功能增强。
- 为服务提供 spring-telqos 支持。
- 优化配置文件中的默认值名称。
  - conf/bitalarm/push.properties
  - conf/bitalarm/source.properties
- 优化 DctiKafkaSource 的处理性能。
- 消除预设配置文件中的真实的 ip 地址。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.3_20200828_build_A

#### 功能构建

- 修正程序在dubbo中注册的应用名称。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.2_20200512_build_A

#### 功能构建

- 将部分实体的Crud服务升级为BatchCrud服务。
  - com.dwarfeng.bitalarm.stack.service.AlarmSettingMaintainService

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.1_20200512_build_A

#### 功能构建

- 完善@Transactional注解的回滚机制。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.0_20200507_build_A

#### 功能构建

- 为报警模态实体添加pointId属性。
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmInfo
  - com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm
- 升级subgrade依赖至1.0.1.a，以避免潜在的RedisDao的分页bug。

#### Bug修复

- 修正部分AlarmHistory实体错误的JSON输入输出字段名称。
  - com.dwarfeng.bitalarm.sdk.bean.entity.FastJsonAlarmHistory
  - com.dwarfeng.bitalarm.sdk.bean.entity.JSFixedFastJsonAlarmHistory
  - com.dwarfeng.bitalarm.sdk.bean.entity.WebInputAlarmHistory

#### 功能移除

- (无)

---

### Release_1.2.2_20200503_build_A

#### 功能构建

- 改动CriteriaMaker，使得其兼容 Dubbo RPC 框架下的数据类型问题。
  - com.dwarfeng.bitalarm.impl.dao.preset.AlarmHistoryPresetCriteriaMaker
  - com.dwarfeng.bitalarm.impl.dao.preset.AlarmHistoryPresetCriteriaMaker

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.1_20200430_build_A

#### 功能构建

- 优化查询预设AlarmSettingMaintainService.CHILD_FOR_POINT。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20200430_build_A

#### 功能构建

- 取消AlarmSetting和AlarmHistory的级联关系。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.0_20200427_build_A

#### 功能构建

- 实现实体维护并通过单元测试。
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmTypeIndicator
- 升级subgrade依赖至1.0.0.a，修复轻微不兼容的错误。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.1_20200425_build_A

#### 功能构建

- 添加com.dwarfeng.bitalarm.sdk.bean.entity.WebInputAlarmInfo。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.0_20200422_build_A

#### 功能构建

- 实现实体并通过单元测试。
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmSetting
  - com.dwarfeng.bitalarm.stack.bean.entity.CurrentAlarm
- 实现报警处理核心逻辑。
- 实现报警实体维护服务。
- 实现程序节点。
  - node-all
  - node-alarm
  - node-maintain
- 编写节点的装配文件，实现节点的自动打包。
- 编写README.md说明文件。

#### Bug修复

- (无)

#### 功能移除

- (无)
