#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

MKIMAGE=${HOST_DIR}/usr/bin/mkimage
MKIMAGE_IN=${BINARIES_DIR}/br2-ucls1012a.its
MKIMAGE_OUT=${BINARIES_DIR}/part0-000000.itb
${MKIMAGE} -f ${MKIMAGE_IN} ${MKIMAGE_OUT}
