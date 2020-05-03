# ChangeLog

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
