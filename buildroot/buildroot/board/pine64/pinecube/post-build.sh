#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

BOARD_DIR="$(dirname "$0")"

install -m 0644 -D "$BOARD_DIR"/extlinux.conf "$BINARIES_DIR"/extlinux/extlinux.conf
