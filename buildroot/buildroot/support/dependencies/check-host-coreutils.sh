#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

# Exit on the first error
set -e

# Does ln supports the --relative/-r option?
ln --relative --help >/dev/null 2>&1

# Does realpath exist?
realpath --help >/dev/null 2>&1

echo OK
