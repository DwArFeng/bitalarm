# bitalarm

一款开箱即用的bit位报警处理项目。

bit位报警，指的是通过一个byte数组发送所有的报警信息。byte数组中，每一个byte中的每一位记录一个报警的状态: 0代表没有报警; 1代表
有报警。一个byte能够传输8个报警状态，做到报警状态的高效传输。

bit位报警内容是约定的，一个byte数组中的每一位报警都有对应的定义，这些定义是事先约定好的，在byte数组中不体现。

---

## 安装说明

1. 下载源码

   使用git进行源码下载。
   ```
   git clone git@github.com:DwArFeng/fdr-manager.git
   ```
   对于中国用户，可以使用gitee进行高速下载。
   ```
   git clone git@gitee.com:dwarfeng/fdr-manager.git
   ```
   
2. 项目打包

   进入项目根目录，执行maven命令
   ```
   mvn clean package
   ```
   
3. 解压

   找到打包后的目标文件 
   ```
   bitalarm-node/bitalarm-node-all/target/bitalarm-node-all-[version]-release.tar.gz
   bitalarm-node/bitalarm-node-alarm/target/bitalarm-node-alarm-[version]-release.tar.gz
   bitalarm-node/bitalarm-node-maintain/target/bitalarm-node-maintain-[version]-release.tar.gz
   bitalarm-node/bitalarm-node-all/target/bitalarm-node-all-[version]-release.tar.gz
   ```
   将其解压至windows系统或者linux系统
   
4. 配置

   1. 进入工程下的`bin`目录，修改所有执行脚本的`basedir`和`logdir`
      
   2. 修改conf文件夹下的配置文件，着重修改各连接的url与密码。
   
5. enjoy it

## 分布式说明

该项目使用`dubbo`作为RPC框架，本身支持分布式，但是在实际使用过程中，由于项目本身的逻辑所致，该项目分布式部署应该遵守如下规则，
否则会导致报警处理流程的行为不正常：

1. 在一个独立的项目中，`node-all`与`node-alarm`的数量加起来不大于1个，即一个项目中，只保留一个报警分析模块。
2. 在任何情况下，`node-maintain`模块的数量允许部署任意多个。

---

## 项目的工作流程

1. 项目运行后，数据源`Source`会不断地将待采集的报警数据交给报警服务`AlarmService`处理。

2. `AlarmService`调用`AlarmHandler`，分析待采集数据报警信息。报警信息的分析与报警数据的顺序严格相关，因此报警分析逻辑使用
公平锁严格的保证多线程的执行顺序。

   `AlarmHandler`分析完报警数据后，决定报警信息是否进行如下的动作。
   1. 报警信息记录
   2. 报警历史记录
   
   每个动作拥有与之对应的事件，当动作确定执行时，会同时生成一个相对应的事件，事件列表如下。
   1. 报警信息事件
   2. 报警历史事件
   
   `AlarmHandler` 将一个报警数据转化成一个或多个动作事件，将这些动作事件交给消费处理器`ConsumeHandler`。
   
3. `ConsumeHandler`消费动作事件，将动作相关的实体持久化至数据访问层中，并将事件推送给`PushHandler`。

   `ConsumeHandler` 拥有灵活的执行逻辑，这些逻辑细节都可以进行配置，详见配置文件`conf/bitalarm/consume.properties`。

---

## 特性

- 通过项目的维护模块建立并维护事先定义的报警内容。

- 通过多种数据源接收dcti标准数据采集信息，并解析base64编码的byte数组，最终提供报警的详细信息。

- 通过查询模块方便的查询当前正在发生的所有报警。

- 通过查询模块方便的查询历史报警，包括每一个报警的报警源、内容、报警类型、起始时间、结束时间。

---

## 项目的使用

### 报警设置的定义

在bitalarm中，您只需要配置报警设置`AlarmSetting`，便可使用该项目。
使用报警信息维护服务`AlarmSettingMaintainService`的相关方法即可向项目中维护`AlarmSetting`实体对象。

`AlarmSetting`的具体字段设置如下：

|字段名称|字段类型|说明|
|---|---|---|
|key|LongIdKey|报警设置的主键，如果为null，在添加时会自动生成|
|pointId|long|数据点的主键，标志着该报警设置属于哪个数据点|
|enabled|boolean|使能标记，标志着该条报警记录是否生效|
|index|int|报警序号位，用于表示该报警对应的数据点传入数据的哪一个比特位|
|alarmMessage|String|报警消息，报警发生时显示的报警文本|
|alarmType|byte|报警类型，根据不同的项目具体设置，比如0为警告，1为错误，2为致命错误|
|remark|String|备注|

### 报警信息

报警信息`AlarmInfo`对象与报警设置`AlarmSetting`为一对一关系，其主键一一对应。

`AlarmInfo` 指示了与之对应的`AlarmSetting`**最新**的状态。

`AlarmInfo`在一般情况下不需要维护，它会随着该项目对报警信息的处理自动生成，只需要查询即可。
但是您也可以调用`AlarmInfoMaintainService`对该实体作出维护。

当`AlarmSetting`被删除时，与其对应的`AlarmInfo`会一并被删除，请注意。

`AlarmInfo`的具体字段设置如下：

|字段名称|字段类型|说明|
|---|---|---|
|key|LongIdKey|报警信息的主键，注意应该与报警设置的主键一一对应|
|index|int|报警序号位，用于表示该报警对应的数据点传入数据的哪一个比特位|
|alarmMessage|String|报警消息，报警发生时显示的报警文本|
|alarmType|byte|报警类型，根据不同的项目具体设置，比如0为警告，1为错误，2为致命错误|
|alarmType|byte|报警类型，根据不同的项目具体设置，比如0为警告，1为错误，2为致命错误|
|happenedDate|Date|发生日期，指示着该报警信息对应的报警设置最近的变更日期|
|alarming|boolean|是否正在报警标识，true代表着正在报警，false代表没有报警|

### 当前报警

报警信息`CurrentAlarm`对象与报警设置`AlarmSetting`为一对一关系，其主键一一对应。

`CurrentAlarm` 指示了与之对应的`AlarmSetting`**正在发生报警且报警没有消失**的状态。

`CurrentAlarm`在一般情况下不需要维护，它会随着该项目对报警信息的处理自动生成，只需要查询即可。
但是您也可以调用`AlarmInfoMaintainService`对该实体作出维护。

当`AlarmSetting`被删除时，与其对应的`CurrentAlarm`会一并被删除，请注意。

`CurrentAlarm`的具体字段设置如下：

|字段名称|字段类型|说明|
|---|---|---|
|key|LongIdKey|报警信息的主键，注意应该与报警设置的主键一一对应|
|index|int|报警序号位，用于表示该报警对应的数据点传入数据的哪一个比特位|
|alarmMessage|String|报警消息，报警发生时显示的报警文本|
|alarmType|byte|报警类型，根据不同的项目具体设置，比如0为警告，1为错误，2为致命错误|
|happenedDate|Date|发生日期，指示着对应的报警设置发生报警的最近日期|

### 报警历史

报警信息`AlarmHistory`对象与报警设置`AlarmSetting`为多对一关系。

当某一个报警信息的报警状态从没有报警变为报警，再从报警变为没有报警后，一条报警历史信息便会记录。

`AlarmHistory`在一般情况下不需要维护，它会随着该项目对报警信息的处理自动生成，只需要查询即可。
但是您也可以调用`AlarmInfoMaintainService`对该实体作出维护。

当`AlarmSetting`被删除时，与其对应的所有`AlarmHistory`会一并被删除，而且通常情况下，会花费较长的时间，请注意。

`AlarmHistory`的具体字段设置如下：

|字段名称|字段类型|说明|
|---|---|---|
|key|LongIdKey|报警信息的主键。|
|alarmSettingKey|LongIdKey|报警信息的外键，与报警设置的主键对应|
|index|int|报警序号位，用于表示该报警对应的数据点传入数据的哪一个比特位|
|alarmMessage|String|报警消息，报警发生时显示的报警文本|
|alarmType|byte|报警类型，根据不同的项目具体设置，比如0为警告，1为错误，2为致命错误|
|startDate|Date|开始日期，指示着报警发生时的日期|
|endDate|Date|结束日期，指示着报警消失时的日期|
|duration|long|持续时间，指示着报警持续的毫秒数|

### 事件推送

事件推送机制保证了获取报警信息的实时性。

当报警信息变化`alarmUpdated`，报警历史记录`historyRecorded`时，会立即生成事件，并通过`PushHandler`进行推送。

`PushHandler`可以通过配置更改其行为，具体的配置参照文件`conf/bitalarm/push.proeprties`

### 数据查询

全部实体对象均可通过其维护服务进行查询，共四个实体均可进行主键查询和全体成员查询。
另外，`AlarmSetting`和`AlarmHistory`实体还提供模板查询。

所有样板查询

* `AlarmSettingMaintainService`

  |模板名称|模板说明|
  |---|---|
  |child_for_point|指定数据点下的所有报警设置|
  |enabled_child_for_point|指定数据点下的所有`enable`属性为`true`的报警设置|
  |alarm_message_like|含有特定报警信息片段的所有报警设置|
  |alarm_type_equals|报警等级为指定值的所有报警设置|

* `AlarmHistoryMaintainService`

  |模板名称|模板说明|
  |---|---|
  |child_for_alarm_setting|属于指定报警设置的报警历史|
  |alarm_message_like|含有特定报警信息判断的报警历史|
  |alarm_type_equals|报警等级为指定值的报警历史|
  |start_date_between|报警开始日期介于指定区间的报警历史|
  |end_date_between|报警结束日期介于指定区间的报警历史|
  |child_for_alarm_setting_start_date_between|属于指定报警设置且报警开始日期介于指定区间的报警历史|
  |child_for_alarm_setting_end_date_between|属于指定报警设置且报警结束日期介于指定区间的报警历史|
  |duration_gt|报警时长大于指定值的报警|
  |duration_lt|报警时长小于指定值的报警|
  |child_for_alarm_setting_duration_gt|属于指定报警设置且报警时长大于指定值的报警|
  |child_for_alarm_setting_duration_lt|属于指定报警设置且报警时长小于指定值的报警|

---

## 插件

该项目可以进行插件扩展，将自己编写的插件放在项目的 `libext` 文件夹中，并且在 `optext` 中编写spring加载文件，以实现插件的加载。

*注意：`optext` 目录下的spring加载文件请满足`opt*.xml`的格式，即以opt开头，以.xml结尾。*

---

## 项目的扩展

1. 数据源的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.Source` 并将实现类注入到spring的IoC容器中。
   
2. 推送器的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.TriggerMaker` 并将实现类注入到spring的IoC容器中。
   
   设定配置文件 `conf/fdr/push.properties`
   ```properties
   ###################################################
   #                     global                      #
   ###################################################
   # 当前的推送器类型。
   # 目前该项目支持的推送器类型有:
   #   drain: 简单的丢弃掉所有消息的推送器。
   #   partial_drain: 丢弃掉部分消息的推送器。
   #   multi: 同时将消息推送给所有代理的多重推送器。
   #   kafka: 基于Kafka消息队列的推送器。
   #
   # 对于一个具体的项目，很可能只用一个推送器。此时希望加载
   # 推送器时只加载需要的那个，其余的推送器不加载。这个需求
   # 可以通过编辑 application-context-scan.xml 实现。
   pusher.type=kafka
   ```
   将 pusher.type 改为扩展的 pusher。
