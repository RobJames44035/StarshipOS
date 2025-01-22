#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

BOARD_DIR="$(dirname $0)"

cp -f ${BOARD_DIR}/grub.cfg ${BINARIES_DIR}/efi-part/EFI/BOOT/grub.cfg
