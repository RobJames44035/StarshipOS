#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

CURRENT_DIR="${PWD}"
KERNEL_DIR="${CURRENT_DIR}/starship_kernel"
BUILD_DIR="${CURRENT_DIR}/build"
BOOT_DIR="${BUILD_DIR}/boot"
KERNEL_IMAGE_PATH="${KERNEL_DIR}/arch/i386/boot/bzImage"
TARGET_KERNEL_PATH="${BOOT_DIR}/starship"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting kernel build script."

if [ ! -d "$BUILD_DIR" ]; then
    log "Building kernel as build directory does not exist..."

    cd "$KERNEL_DIR"
    log "Cleaning previous builds..."
    make clean

    log "Setting up tinyconfig..."
    make tinyconfig

    log "Building the kernel..."
    make

    cd "$CURRENT_DIR"
    log "Creating directory structure for boot..."
    mkdir -p "$BOOT_DIR"

    log "Copying kernel image to boot directory..."
    cp "$KERNEL_IMAGE_PATH" "$TARGET_KERNEL_PATH"

    log "Kernel build and setup completed successfully."
else
    log "Build directory already exists. Skipping kernel build."
fi
