#!/bin/bash
# 程序的根目录
basedir=/usr/local/bitalarm-all

PID=$(cat $basedir/bitalarm.pid)
kill "$PID"
