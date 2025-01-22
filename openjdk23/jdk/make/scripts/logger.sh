#!/bin/bash
#
# StarshipOS Copyright (c) 2012-2025. R.A. James
#

# Usage: ./logger.sh thelogfile acommand arg1 arg2
#
# Execute acommand with args, in such a way that
# both stdout and stderr from acommand are appended to
# thelogfile.
#
# Preserve stdout and stderr, so that the stdout
# from logger.sh is the same from acommand and equally
# for stderr.
#
# Propagate the result code from acommand so that
# ./logger.sh exits with the same result code.

# Create a temporary directory to store the result code from
# the wrapped command.
RCDIR=`mktemp -dt jdk-build-logger.tmp.XXXXXX` || exit $?
trap "rm -rf \"$RCDIR\"" EXIT
LOGFILE=$1
shift

# We need to handle command likes like "VAR1=val1 /usr/bin/cmd VAR2=val2".
# Do this by shifting away prepended variable assignments, and export them
# instead.
is_prefix=true
for opt; do
  if [[ "$is_prefix" = true && "$opt" =~ ^.*=.*$ ]]; then
    export $opt
    shift
  else
    is_prefix=false
  fi
done

(exec 3>&1 ; ("$@" 2>&1 1>&3; echo $? > "$RCDIR/rc") | tee -a $LOGFILE 1>&2 ; exec 3>&-) | tee -a $LOGFILE
exit `cat "$RCDIR/rc"`
