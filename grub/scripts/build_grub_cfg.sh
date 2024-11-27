#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

BUILD_DIR="build/boot"
OUTPUT_FILE="$BUILD_DIR/grub.cfg"

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting GRUB configuration script."

if [ ! -d "$BUILD_DIR" ]; then
    log "Creating build directory: $BUILD_DIR"
    mkdir -p "$BUILD_DIR"

    log "Creating GRUB configuration file: $OUTPUT_FILE"
    cat <<EOF > "$OUTPUT_FILE"
# GRUB Configuration file

# Set the default boot entry to the first entry in the menu
set default=0

# Set the timeout before the default boot entry is selected
set timeout=5

menuentry "Starship" {
set root=(cd)
linux /boot/starship root=live:LABEL=StarshipOS rw console=ttyS0,115200 loglevel=7 earlyprintk=serial,ttyS0,115200 debug initcall_debug ignore_loglevel
initrd /boot/initramfs.img
}
EOF
    log "Successfully created GRUB configuration file."
else
    log "Build directory already exists. Skipping creation."
fi

log "Finished GRUB configuration script."
