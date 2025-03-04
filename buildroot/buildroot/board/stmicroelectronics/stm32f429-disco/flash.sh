#!/bin/bash

#
# StarshipOS Copyright (c) 2025. R.A. James
#

OUTPUT_DIR=$1
BOARD_NAME=${2:-stm32f429discovery}

if ! test -d "${OUTPUT_DIR}" ; then
    echo "ERROR: no output directory specified."
    echo "Usage: $0 OUTPUT_DIR BOARD_NAME"
    echo ""
    echo "Arguments:"
    echo "    OUTPUT_DIR    The Buildroot output directory."
    echo "    BOARD_NAME    One of the available boards among:"
    echo "                  stm32f429discovery, stm32f429disc1"
    exit 1
fi

${OUTPUT_DIR}/host/bin/openocd -f board/${BOARD_NAME}.cfg \
  -c "init" \
  -c "reset init" \
  -c "flash probe 0" \
  -c "flash info 0" \
  -c "flash write_image erase ${OUTPUT_DIR}/images/stm32f429i-disco.bin 0x08000000" \
  -c "flash write_image erase ${OUTPUT_DIR}/images/stm32f429-disco.dtb 0x08004000" \
  -c "flash write_image erase ${OUTPUT_DIR}/images/xipImage 0x0800C000" \
  -c "reset run" \
  -c "shutdown"
