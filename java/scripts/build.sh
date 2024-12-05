#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

# Define variables
CURRENT_DIR="${PWD}"
CONFIGURATION="linux-x86_64-minimal-fastdebug"
BUILD_DIR="${CURRENT_DIR}/build"
JDK_ARCHIVE_URL="https://download.java.net/java/GA/jdk23.0.1/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-23.0.1_linux-x64_bin.tar.gz"
JDK_ARCHIVE_NAME="openjdk-23.0.1_linux-x64_bin.tar.gz"
JDK_DIR="${CURRENT_DIR}/jdk"
BOOT_JDK="${CURRENT_DIR}/jdk-23.0.1"
PREFIX_DIR="${CURRENT_DIR}/build/bin"
EXEC_PREFIX_DIR="${CURRENT_DIR}/build/lib"
FINAL_BUILD_DIR="${CURRENT_DIR}/build/jdk"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# Function to download and unpack JDK archive
download_and_unpack_jdk() {
    log "Downloading JDK archive..."
    wget --progress=bar "$JDK_ARCHIVE_URL" -O "$JDK_ARCHIVE_NAME"
    log "Unpacking JDK archive..."
    tar -xzf "$JDK_ARCHIVE_NAME"
    rm "$JDK_ARCHIVE_NAME"
}

# Function to configure and build the JDK
configure_and_build_jdk() {
    cd "$JDK_DIR"
    make CONF="${CONFIGURATION}" clean
    log "Configuring build with boot JDK..."
    ./configure \
        --with-boot-jdk="$BOOT_JDK" \
        --with-jvm-variants=minimal \
        --enable-libffi-bundling \
        --with-jvm-features="compiler1,compiler2,zgc" \
        --enable-debug \
        --prefix="$PREFIX_DIR" \
        --exec-prefix="$EXEC_PREFIX_DIR"

#    log "Cleaning previous builds if any..."
#    make CONF="${CONFIGURATION}" clean
    log "Building JDK..."
    make CONF="${CONFIGURATION}" images
    cd "$CURRENT_DIR"
}

# Main script logic
log "Starting JDK build script."

if [ ! -d "$BUILD_DIR" ]; then
#    download_and_unpack_jdk
    configure_and_build_jdk

    log "Removing BOOT_JDK directory..."
    rm -rf "$BOOT_JDK"

    mkdir -p "${FINAL_BUILD_DIR}"
    log "Copying final JDK build to ${FINAL_BUILD_DIR}..."
    cp -rv "/home/rajames/PROJECTS/StarshipOS/java/jdk/build/linux-x86_64-minimal-fastdebug/jdk" "$FINAL_BUILD_DIR"
else
    log "Nothing to do."
fi

log "Build Java complete."
