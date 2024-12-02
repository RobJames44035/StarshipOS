#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
STARSHIP_ROOT="${HOME}/starship"
KERNEL_DIR="${STARSHIP_ROOT}/starship_kernel"
BUILD_DIR="${STARSHIP_ROOT}/build"
BOOT_DIR="${BUILD_DIR}/boot"
#KERNEL_IMAGE_PATH="${KERNEL_DIR}/arch/x86_64/boot/bzImage"
#TARGET_KERNEL_PATH="${BOOT_DIR}/starship"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting kernel build script."

if [ ! -d "$BUILD_DIR" ]; then
    log "Building kernel as build directory does not exist..."

    cd "$KERNEL_DIR"
#    log "Cleaning previous builds in ${KERNEL_DIR}"
#    sudo make clean
    cp "$STARSHIP_ROOT/.config" "$KERNEL_DIR/.config"

    log "Building the starship kernel in ${KERNEL_DIR}"
    make -j$(nproc) bzImage
    sudo make modules

# Step 3: Create a temporary directory for the live CD structure
    LIVECD_PATH="${HOME}/starship/build/live_cd"
    log "mkdir -p ${LIVECD_PATH}"

    mkdir -p "$LIVECD_PATH"

# Step 4: Install modules into the temporary directory
    log "export INSTALL_MOD_PATH=${LIVECD_PATH}"
    export INSTALL_MOD_PATH=$LIVECD_PATH
    log "make modules_install"
    make modules_install

# Step 5: Create boot directory and copy bzImage
  log "mkdir -p ${LIVECD_PATH}/boot"
  mkdir -p "${LIVECD_PATH}/boot"

  log "cp arch/x86/boot/bzImage ${LIVECD_PATH}/boot/starship"
  cp "arch/x86/boot/bzImage" "${LIVECD_PATH}/boot/starship"

  mkdir -p $HOME/live_cd/build/iso-image

  cp -rv $HOME/starship/build/live_cd/* $HOME/live_cd/build/iso-image

else
    log "Nothing to do!"
fi
