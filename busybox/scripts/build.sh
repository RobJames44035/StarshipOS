#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
BUSYBOX_SRC=$HOME/busybox/busybox
BUSYBOX_DST=$HOME/busybox/build/init_ram_fs

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting busybox build script"

if [ ! -d build ]; then
  log "Building BusyBox..."
  cd "$BUSYBOX_SRC"
  make clean
  make -j$(nproc) all

  log "installing Busybox..."
  mkdir -p "$BUSYBOX_DST"
  make CONFIG_PREFIX="$BUSYBOX_DST" install

  log "Setting proper file permissions & ownership for BusyBox."
  sudo chown root:root "$BUSYBOX_DST/bin/busybox"
  sudo chmod 4755 "$BUSYBOX_DST/bin/busybox"
  cd "$HOME/busybox"
else
  log "Nothing to do."
fi
