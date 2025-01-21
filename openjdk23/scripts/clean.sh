#!/bin/bash

#
# StarshipOS Copyright (c) 2025. R.A. James
#

# Exit immediately if a command exits with a non-zero status
set -e

# Define variables
CURRENT_DIR="$(pwd)"
CONFIGURATION="linux-x86_64-server-release"
BUILD_DIR="build"
JDK_ARCHIVE_URL="https://download.java.net/java/GA/jdk23.0.1/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-23.0.1_linux-x64_bin.tar.gz"
JDK_ARCHIVE_NAME="openjdk-23.0.1_linux-x64_bin.tar.gz"
JDK_DIR="jdk"

# These 4 vars unfortunately need to be abs. paths.
BOOT_JDK="$(pwd)/jdk-23.0.1"
PREFIX_DIR="$(pwd)/build/bin"
EXEC_PREFIX_DIR="$(pwd)/build/lib"
FINAL_BUILD_DIR="$(pwd)/build"

MAKE_DIR="jdk"

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Moving into $MAKE_DIR"
cd "$MAKE_DIR" || { error_log "Failed to change directory to $MAKE_DIR"; exit 1; }

log "Cleaning with make clean in $MAKE_DIR"
make CONF="linux-x86_64-server-release" clean || { log "make CONF=\"linux-x86_64-server-release\" clean failed"; exit 1; }
log "Removing ../build"
rm -rf ../build || { log "ERROR removing ../build"; exit 1; }
cd ../
    log "Downloading BOOT JDK archive..."
    wget --progress=bar "$JDK_ARCHIVE_URL" -O "$JDK_ARCHIVE_NAME"
    log "Unpacking BOOT JDK archive..."
    tar -xzf "$JDK_ARCHIVE_NAME"
    rm "$JDK_ARCHIVE_NAME"
cd "$MAKE_DIR" || { error_log "Failed to change directory to $MAKE_DIR"; exit 1; }
bash ./configure --with-boot-jdk="$BOOT_JDK" --with-jvm-variants=server --enable-libffi-bundling --with-jvm-features="compiler1,compiler2,zgc" --prefix="$PREFIX_DIR" --exec-prefix="$EXEC_PREFIX_DIR" || { log "bash ./configure --wi..."; exit 1; }
cd "../"
log "SUCCESS: Finished JDK23 clean."