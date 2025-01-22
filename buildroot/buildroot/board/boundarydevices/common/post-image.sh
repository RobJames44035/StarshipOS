#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

support/scripts/genimage.sh -c $(dirname $0)/genimage.cfg

exit $?
