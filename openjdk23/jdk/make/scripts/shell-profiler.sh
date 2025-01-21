#!/bin/bash
#
# StarshipOS Copyright (c) 2012-2025. R.A. James
#

# Usage: sh shell-tracer.sh <TIME_CMD_TYPE> <TIME_CMD> <OUTPUT_FILE> <shell command line>
#
# This shell script is supposed to be set as a replacement for SHELL in make,
# causing it to be called whenever make wants to execute shell commands.
# The <shell command line> is suitable for passing on to the old shell,
# typically beginning with -c.
#
# This script will run the shell command line and it will also store a simple
# log of the time it takes to execute the command in the OUTPUT_FILE, using
# utility for time measure specified with TIME_CMD option.
#
# Type of time measure utility is specified with TIME_CMD_TYPE option.
# Recognized options values of TIME_CMD_TYPE option: "gnutime", "flock".

TIME_CMD_TYPE="$1"
TIME_CMD="$2"
OUTPUT_FILE="$3"
shift
shift
shift
if [ "$TIME_CMD_TYPE" = "gnutime" ]; then
  # Escape backslashes (\) and percent chars (%). See man for GNU 'time'.
  msg=${@//\\/\\\\}
  msg=${msg//%/%%}
  "$TIME_CMD" -f "[TIME:%E] $msg" -a -o "$OUTPUT_FILE" "$@"
elif [ "$TIME_CMD_TYPE" = "flock" ]; then
  # Emulated GNU 'time' with 'flock' and 'date'.
  ts=`date +%s%3N`
  "$@"
  status=$?
  ts2=`date +%s%3N`
  millis=$((ts2 - ts))
  ms=$(($millis % 1000))
  seconds=$((millis / 1000))
  ss=$(($seconds % 60))
  minutes=$(($seconds / 60))
  mm=$(($minutes % 60))
  hh=$(($minutes / 60)):
  [ $hh != "0:" ] || hh=
  # Synchronize on this script.
  flock -w 10 "$0" printf "[TIME:${hh}${mm}:${ss}.%.2s] %s\n" $ms "$*" >> "$OUTPUT_FILE" || true
  exit $status
else
  "$@"
fi
