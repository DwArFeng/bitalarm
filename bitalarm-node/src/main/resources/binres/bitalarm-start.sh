#!/bin/sh
# 程序的根目录。
basedir="/usr/local/bitalarm"
# 日志的根目录。
logdir="/var/log/bitalarm"

# JVM 内存设置。
# 如果您希望系统自动分配内存，注释掉下方注释...
jvm_memory_opts="\
  -Xmx100m \
  -XX:MaxMetaspaceSize=130m \
  -XX:ReservedCodeCacheSize=15m \
  -XX:CompressedClassSpaceSize=15m"
# 并打开此注释。
# jvm_memory_opts=""

# JAVA 日志编码配置。
# 如果您希望程序使用默认编码，注释掉下方注释...
java_log_encoding_opts="\
  -Dlog.consoleEncoding=UTF-8 \
  -Dlog.fileEncoding=UTF-8"
# 并打开此注释。
# java_log_encoding_opts=""

# JAVA JMXREMOTE 配置。
# 如果您希望启用 JMX 远程管理，注释掉下方注释...
java_jmxremote_opts=""
# 并打开此注释。
# java_jmxremote_opts="\
#   -Dcom.sun.management.jmxremote.port=23000 \
#   -Dcom.sun.management.jmxremote.authenticate=false \
#   -Dcom.sun.management.jmxremote.ssl=false"

# JAVA 固定配置，请勿编辑此行。
java_fixed_opts="\
  -Dlog.dir=\"$logdir\" \
  -Dlog4j.shutdownHookEnabled=false \
  -Dlog4j2.is.webapp=false"

cd "$basedir" || exit
# shellcheck disable=SC2154
eval \
  nohup /bin/java -classpath "lib/*:libext/*" \
  "$jvm_memory_opts" \
  "$java_log_encoding_opts" \
  "$java_jmxremote_opts" \
  "$java_fixed_opts" \
  "${mainClass}" \
  >/dev/null 2>&1 "&"
echo $! >"$basedir/bitalarm.pid"
