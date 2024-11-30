#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

THIS_DIR="$PWD"
MAKE_DIR="$THIS_DIR/jdk"
CONFIGURATION="linux-x86_64-minimal-fastdebug"

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

function error_log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] ERROR: $1" >&2
}

# Check if the directory exists before changing into it
if [ -d "$MAKE_DIR" ]; then
        log "Moving into $MAKE_DIR"

        # Attempt to change directory and log on error
        cd "$MAKE_DIR" || { error_log "Failed to change directory to $MAKE_DIR"; exit 1; }

        log "Cleaning with make clean in $MAKE_DIR"

        # Run make clean and log on error
        make CONF="${CONFIGURATION}" clean || { error_log "make clean failed"; exit 1; }

        log "Returning to $THIS_DIR"

        # Go back to the original directory
        cd "$THIS_DIR" || { error_log "Failed to switch back to $THIS_DIR"; exit 1; }

        if [ "$UBER_CLEAN" = "true" ]; then
            log "Performing uber-clean: Deleting build directory"
            rm -rf build || { error_log "Failed to delete build directory"; exit 1; }
        fi
else
    error_log "The directory $MAKE_DIR does not exist."
    exit 1
fi
