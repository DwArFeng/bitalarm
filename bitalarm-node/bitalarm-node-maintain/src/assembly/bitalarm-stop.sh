#!/bin/bash
# 程序的根目录
basedir=/usr/local/bitalarm-maintain

PID=$(cat $basedir/bitalarm.pid)
kill "$PID"
