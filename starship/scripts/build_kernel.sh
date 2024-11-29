#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
CURRENT_DIR="${HOME}/starship"
KERNEL_DIR="${CURRENT_DIR}/starship_kernel"
BUILD_DIR="${CURRENT_DIR}/build"
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
    log "Cleaning previous builds in ${KERNEL_DIR}"
    sudo make clean

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

## Step 6: Create other necessary directories (example: /dev, /proc, /sys)
#  log "mkdir -p ${LIVECD_PATH}/.........."
#  mkdir -p $LIVECD_PATH/{dev,proc,sys,run,etc,home,var,tmp,usr,bin,sbin}

# Step 7: move livecd to module build
  mkdir -p $HOME/live_cd/build/iso-image

  cp -rv $HOME/starship/build/live_cd/* $HOME/live_cd/build/iso-image

else
    log "Nothing to do!"
fi
