# ChangeLog

### Release_1.5.0_20210513_build_A

#### 功能构建

- (无)

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
  - com.dwarfeng.bitalarm.stack.bean.entity.AlarmHistory.pointId

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
