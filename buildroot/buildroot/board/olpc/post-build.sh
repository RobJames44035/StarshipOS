#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

BOARD_DIR="$(dirname $0)"
install -m 0644 -D $BOARD_DIR/olpc.fth $TARGET_DIR/boot/olpc.fth
