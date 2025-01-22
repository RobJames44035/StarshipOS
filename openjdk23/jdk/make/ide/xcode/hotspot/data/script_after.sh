#!/bin/bash
#
# StarshipOS Copyright (c) 2017-2025. R.A. James
#

echo "running script_after.sh"

readonly JDK_LIB_PATH="build/jdk/lib/server/libjvm.dylib";

if [ ! -f ${JDK_LIB_PATH} ] ; then
{
    echo ">>>>>>>   Cannot find ${JDK_LIB_PATH}, the build failed!?";
    exit 1;
}
fi
