#!/usr/bin/env bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#

set -e

cp board/avnet/rzboard_v2l/uEnv.txt "${BINARIES_DIR}"
support/scripts/genimage.sh -c board/avnet/rzboard_v2l/genimage.cfg
