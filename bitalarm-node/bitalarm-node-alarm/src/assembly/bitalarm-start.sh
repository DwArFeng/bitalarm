#!/bin/sh
# 程序的根目录
basedir=/usr/local/bitalarm-alarm
# 日志的根目录
logdir=/var/log/bitalarm-alarm

cd $basedir || exit
# shellcheck disable=SC2154
# shellcheck disable=SC2086
nohup /bin/java -classpath "lib/*:libext/*" \
-Dlog.dir=$logdir \
-Dlog.consoleEncoding=UTF-8 \
-Dlog.fileEncoding=UTF-8 \
${mainClass} \
>/dev/null 2>&1 &
echo $! >$basedir/bitalarm.pid
