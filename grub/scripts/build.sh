#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

BLOCK_DEVICE=$1
OUTPUT_FILE="grub.cfg"
TEMPLATE_FILE="src/grub.cfg"
BUILD_DIR="build/$BLOCK_DEVICE/init_ram_fs/boot"

function log() {
    echo "[INFO: $(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting GRUB configuration script."

if [ ! -d "$BUILD_DIR" ]; then
    log "Creating build directory: $BUILD_DIR"
    mkdir -p "$BUILD_DIR"

    log "Creating GRUB configuration file: $OUTPUT_FILE"
    sed "s/@@X_1@@/$BLOCK_DEVICE/g" "$TEMPLATE_FILE" > "build/$BLOCK_DEVICE/$OUTPUT_FILE"

    log "Successfully created GRUB configuration file."
else
    log "Build directory already exists. Skipping creation."
fi

log "Finished GRUB configuration script."
