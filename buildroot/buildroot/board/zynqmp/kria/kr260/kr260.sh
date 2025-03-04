#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

# This is a temporary work around for generating kr260 u-boot.itb.
# The problem is there is no way to currently configure u-boot to apply
# the carrier board dtb overlay during build, so all kr260 carrier board
# drivers are missing.
# This will be removed when u-boot can build the kr260 u-boot.itb natively.

UBOOT_DIR="$4"

fdtoverlay -o "${UBOOT_DIR}/fit-dtb.blob" \
	   -i "${UBOOT_DIR}/arch/arm/dts/zynqmp-smk-k26-revA.dtb" \
	   "${UBOOT_DIR}/arch/arm/dts/zynqmp-sck-kr-g-revB.dtbo"

"${UBOOT_DIR}/tools/mkimage" -E -f "${UBOOT_DIR}/u-boot.its" \
			     -B 0x8 "${BINARIES_DIR}/u-boot.itb"
