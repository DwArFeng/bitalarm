#!/bin/sh

# Set the root directory of the program.
basedir="$(cd "$(dirname "$0")/.." && pwd)"

# JVM memory settings.
# If you want the program to automatically allocate memory, please comment out the content below...
jvm_memory_opts="\
-Xmx100m \
-XX:MaxMetaspaceSize=130m \
-XX:ReservedCodeCacheSize=15m \
-XX:CompressedClassSpaceSize=15m"
# and uncomment the content below.
# jvm_memory_opts=""

# JAVA JMXREMOTE configuration.
# If you want to enable JMX remote management, please comment out the content below...
java_jmxremote_opts=""
# and uncomment the content below.
# java_jmxremote_opts="\
# -Dcom.sun.management.jmxremote.port=23000 \
# -Dcom.sun.management.jmxremote.authenticate=false \
# -Dcom.sun.management.jmxremote.ssl=false"

# JAVA logging configuration.
# Fixed configuration, please do not edit this line.
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
