#!/bin/sh

# 设置程序的根目录。
basedir="$(cd $(dirname $0)/.. && pwd)"

# JVM 内存设置。
# 如果您希望系统自动分配内存，请注释下方内容...
jvm_memory_opts="\
-Xmx100m \
-XX:MaxMetaspaceSize=130m \
-XX:ReservedCodeCacheSize=15m \
-XX:CompressedClassSpaceSize=15m"
# 并取消下方注释。
# jvm_memory_opts=""

# JAVA JMXREMOTE 配置。
# 如果您希望启用 JMX 远程管理，请注释下方内容...
java_jmxremote_opts=""
# 并取消下方注释。
# java_jmxremote_opts="\
# -Dcom.sun.management.jmxremote.port=23000 \
# -Dcom.sun.management.jmxremote.authenticate=false \
# -Dcom.sun.management.jmxremote.ssl=false"

# JAVA 日志配置。
# 固定配置，请勿编辑此行。
java_logging_opts="\
-Dlog4j2.configurationFile=confext/logging-settings.xml,conf/logging/settings.xml \
-Dlog4j.shutdownHookEnabled=false \
-Dlog4j2.is.webapp=false"

cd "$basedir" || exit
# shellcheck disable=SC2154
eval \
nohup /bin/java -classpath "lib/*:libext/*" \
"$jvm_memory_opts" \
"$java_jmxremote_opts" \
"$java_logging_opts" \
"${mainClass}" \
>/dev/null 2>&1 "&"
echo $! >"$basedir/bitalarm.pid"
