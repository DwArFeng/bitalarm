#!/bin/sh
# 程序的根目录
basedir="/usr/local/bitalarm"

PID=$(cat "$basedir/bitalarm.pid")
kill "$PID"
