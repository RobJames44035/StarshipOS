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
LIVE_CD_PATH="$HOME/starship/build/init_ram_fs" # Really for the live cd
export INSTALL_MOD_PATH=$LIVE_CD_PATH

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting kernel build script."

if [ ! -d "$BUILD_DIR" ]; then
  log "Building kernel because the build directory does not exist."
  cd "$KERNEL_DIR"
  log "Building the starship kernel in $KERNEL_DIR"

  sudo make -j$(nproc) bzImage
  sudo make modules

  log "mkdir -p ${LIVE_CD_PATH}"
  mkdir -p "$LIVE_CD_PATH"
  log "make modules_install"
  make modules_install

  mkdir -p "${LIVE_CD_PATH}/boot"
  log "Copying kernel to ${LIVE_CD_PATH}/boot"
  cp "arch/x86/boot/bzImage" "$LIVE_CD_PATH/boot/starship"

else
    log "Nothing to do!"
fi
