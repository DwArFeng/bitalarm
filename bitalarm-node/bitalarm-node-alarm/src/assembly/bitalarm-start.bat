@echo off

rem 程序的根目录
SET basedir="C:\Program Files\bitalarm-alarm"
rem 日志的根目录
SET logdir="%basedir%\logs"

rem 打开目录，执行程序
cd %basedir%
java -classpath "lib\*;libext\*" ^
-Dlog.dir=%logdir% ^
-Dlog.consoleEncoding=GBK ^
-Dlog.fileEncoding=UTF-8 ^
${mainClass}

@pause
