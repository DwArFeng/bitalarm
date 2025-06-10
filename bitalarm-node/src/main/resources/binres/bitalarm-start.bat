@echo off

rem 设置程序的根目录。
cd /d "%~dp0.."
SET "basedir=%cd%"

rem JVM 内存设置。
rem 如果您希望系统自动分配内存，请注释下方内容...
SET jvm_memory_opts=^
-Xmx100m ^
-XX:MaxMetaspaceSize=130m ^
-XX:ReservedCodeCacheSize=15m ^
-XX:CompressedClassSpaceSize=15m
rem 并取消下方注释。
rem SET jvm_memory_opts=

rem JAVA JMXREMOTE 配置。
rem 如果您希望启用 JMX 远程管理，请注释下方内容...
SET java_jmxremote_opts=
rem 并取消下方注释。
rem SET java_jmxremote_opts=^
rem -Dcom.sun.management.jmxremote.port=23000 ^
rem -Dcom.sun.management.jmxremote.authenticate=false ^
rem -Dcom.sun.management.jmxremote.ssl=false

rem JAVA 日志配置。
rem 固定配置，请勿编辑此行。
SET java_logging_opts=^
-Dlog4j2.configurationFile=confext/logging-settings.xml,conf/logging/settings.xml ^
-Dlog4j.shutdownHookEnabled=false ^
-Dlog4j2.is.webapp=false

rem 打开目录，执行程序。
cd "%basedir%"
start "Bitalarm" /MAX ^
java -classpath "lib\*;libext\*" ^
%jvm_memory_opts% ^
%java_jmxremote_opts% ^
%java_logging_opts% ^
${mainClass}
