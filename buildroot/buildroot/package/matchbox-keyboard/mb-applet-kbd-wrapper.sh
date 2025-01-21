#!/bin/sh
#
# StarshipOS Copyright (c) 2025. R.A. James
#

#

killall matchbox-keyboard
if [ ! $? -eq 0 ]
then
    matchbox-keyboard &
fi
