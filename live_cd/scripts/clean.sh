#!/bin/bash

# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

BUILD_DIR="./build"

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "GRUB will be 'Uber-clean'."
if [ -d "$BUILD_DIR" ]; then
  sudo rm -rf "$BUILD_DIR"
else
  log "Nothing to do."
fi

log "GRUB will be rebuilt every run."
