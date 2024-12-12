#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

# Define paths and environment
PROJECT_HOME="/home/rajames/PROJECTS/StarshipOS"
MODULE_ROOT_DIR="${PROJECT_HOME}/starship"
KERNEL_SRC_DIR="${MODULE_ROOT_DIR}/starship_kernel"
BUILD_DIR="${MODULE_ROOT_DIR}/build"
LIVE_IMAGE_DIR="${BUILD_DIR}/init_ram_fs"  # Really for the live CD
export INSTALL_MOD_PATH="${LIVE_IMAGE_DIR}"

# Logging function to show progress
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# Start the script workflow
log "Starting kernel build script."

# Check if $BUILD_DIR exists
if [ ! -d "$BUILD_DIR" ]; then
    log "No build directory detected. Starting kernel build process."

    # Ensure kernel directory exists before proceeding
    if [ ! -d "$KERNEL_SRC_DIR" ]; then
        log "Kernel directory does not exist: $KERNEL_SRC_DIR"
        log "Exiting script as there is no source to build."
        exit 1
    fi

    # Enter kernel directory
    cd "$KERNEL_SRC_DIR"
    log "Building the Starship kernel in $KERNEL_SRC_DIR."

    # Compile kernel image and modules
    log "Compiling with make -j$(nproc) bzImage."
    sudo make -j$(nproc) bzImage
    log "Compiling kernel modules."
    sudo make modules

    # Prepare Live CD path for installation
    log "Creating Live CD path: $LIVE_IMAGE_DIR"
    mkdir -p "$LIVE_IMAGE_DIR"

    log "Installing modules into: $LIVE_IMAGE_DIR"
    make modules_install

    # Copy kernel to boot directory
    log "Preparing boot directory: ${LIVE_IMAGE_DIR}/boot"
    mkdir -p "${LIVE_IMAGE_DIR}/boot"
    log "Copying bzImage (kernel) to ${LIVE_IMAGE_DIR}/boot/starship."
    cp "arch/x86/boot/bzImage" "${LIVE_IMAGE_DIR}/boot/starship"

    log "Kernel build and installation completed successfully."
else
    # Skip build if $BUILD_DIR already exists
    log "Build directory already exists. Skipping kernel build."
    log "The assembly process will now handle attaching the artifacts."
fi
