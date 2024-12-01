#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME=$PWD
KERNEL_ROOT=$PWD/starship/starship_kernel

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

cd "$KERNEL_ROOT"
make clean
make xconfig
cd "$HOME"
