#!/bin/sh
#
# StarshipOS Copyright (c) 2025. R.A. James
#

set -e

shift
for file in "$@"; do
	cp -f "${file}" "${TARGET_DIR}/root/"
done
