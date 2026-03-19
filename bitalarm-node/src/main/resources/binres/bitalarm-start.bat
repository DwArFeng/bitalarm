@echo off

rem Set the root directory of the program.
cd /d "%~dp0.."
SET "basedir=%cd%"

rem JVM memory settings.
rem If you want the program to automatically allocate memory, please comment out the content below...
SET jvm_memory_opts=^
-Xmx100m ^
-XX:MaxMetaspaceSize=130m ^
-XX:ReservedCodeCacheSize=15m ^
-XX:CompressedClassSpaceSize=15m
rem and uncomment the content below.
rem SET jvm_memory_opts=

rem JAVA JMXREMOTE configuration.
rem If you want to enable JMX remote management, please comment out the content below...
SET java_jmxremote_opts=
rem and uncomment the content below.
rem SET java_jmxremote_opts=^
rem -Dcom.sun.management.jmxremote.port=23000 ^
rem -Dcom.sun.management.jmxremote.authenticate=false ^
rem -Dcom.sun.management.jmxremote.ssl=false

rem JAVA logging configuration.
rem Fixed configuration, please do not edit this line.
SET java_logging_opts=^
-Dlog4j2.configurationFile=confext/logging-settings.xml,conf/logging/settings.xml ^
-Dlog4j.shutdownHookEnabled=false ^
-Dlog4j2.is.webapp=false

rem Open directory and execute program.
cd "%basedir%"
start "Bitalarm" /MAX ^
java -classpath "lib\*;libext\*" ^
%jvm_memory_opts% ^
%java_jmxremote_opts% ^
%java_logging_opts% ^
${mainClass}
