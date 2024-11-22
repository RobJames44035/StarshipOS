#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

BUILD_DIR="build"
TARGET_DIR="target/src"
BUSYBOX_VERSION="1.35.0"
BUSYBOX_ARCHIVE="busybox-${BUSYBOX_VERSION}.tar.bz2"
BUSYBOX_URL="https://busybox.net/downloads/${BUSYBOX_ARCHIVE}"
BUSYBOX_SRC_DIR="${TARGET_DIR}/busybox-${BUSYBOX_VERSION}"

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting busybox build script."
if [ ! -d build ]; then
  log "Creating build directory."
  mkdir -p $TARGET_DIR
  cd $TARGET_DIR

  log "Downloading busybox."
  wget $BUSYBOX_URL

  log "Downloading busybox."
  tar -xjvf $BUSYBOX_ARCHIVE

  log "Entering ./busybox-$BUSYBOX_VERSION."
  cd ./busybox-$BUSYBOX_VERSION

  log "Creating the default (defconfig) configuration."
  make defconfig

  log "Running make (default target)."
  make

  log "Running make CONFIG_PREFIX=../../../build install."
  make CONFIG_PREFIX=../../../build install

  log "Applying proper file ownership and permissions."
  sudo chown root:root ../../../build/bin/busybox
  sudo chmod 4755 ../../../build/bin/busybox
else
  log "Nothing to do."
fi
log "Finished busybox build script."
