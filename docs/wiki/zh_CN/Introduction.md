# bitalarm

一款开箱即用的 bit 位报警处理项目。

所谓的 bit 位报警，指的是通过一个 byte 数组发送所有的报警信息。
在 byte 数组中，每一个 byte 中的每一位记录一个报警的状态，在其中：

- `0`: 没有报警。
- `1`: 有报警。

一个 byte 能够传输 8 个报警状态，做到报警状态的高效传输。

需要注意的是，bit 位报警内容是约定的。
一个 byte 数组中的每一位报警都有对应的定义，这些定义是事先约定好的，在 byte 数组中不体现。

## 特性

1. Subgrade 架构支持。
2. 完整的 bit 报警机制实现。
3. 多种数据源适配。
4. 提供 QoS 运维平台，能够在前端页面、GUI 尚未开发完成的环境下使用本服务的功能，并进行运维操作。
5. 关键数据支持数据标记，为运维调试、数据迁移、数据追溯提供一定的辅助作用。

## 文档

该项目的文档位于 [docs](../../../docs) 目录下，包括：

### wiki

wiki 为项目的开发人员为本项目编写的详细文档，包含不同语言的版本，主要入口为：

1. [简介](./Introduction.md) - 镜像的 `README.md`，与本文件内容基本相同。
2. [目录](./Contents.md) - 文档目录。

## 安装说明

1. 下载源码

   使用 git 进行源码下载：

   ```shell
   git clone git@github.com:DwArFeng/bitalarm.git
   ```

   对于中国用户，可以使用 gitee 进行高速下载：

   ```shell
   git clone git@gitee.com:dwarfeng/bitalarm.git
   ```

2. 项目打包

   进入项目根目录，执行 maven 命令：

   ```shell
   mvn clean package
   ```

3. 解压

   找到打包后的目标文件：

   ```
   bitalarm-node/target/bitalarm-node-[version]-release.tar.gz
   ```

   将其解压至 windows 系统或者 linux 系统。

4. 配置

   1. 修改 conf 文件夹下的配置文件，着重修改各连接的 url 与密码。

5. enjoy it

## 分布式说明

该项目使用 `dubbo` 作为 RPC 框架，本身支持分布式，您可以在实际使用时，部署该项目任意数量，以进行分布式运算。
