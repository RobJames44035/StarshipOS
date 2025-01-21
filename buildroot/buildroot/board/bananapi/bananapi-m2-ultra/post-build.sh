#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

BOARD_DIR="$(dirname "$0")"

install -m 644 -D "${BOARD_DIR}"/extlinux.conf \
	"${TARGET_DIR}"/boot/extlinux/extlinux.conf
