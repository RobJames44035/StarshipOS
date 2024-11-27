#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

ISO_PATH="/home/rajames/PROJECTS/StarshipOS/live_cd/build/StarshipOS.iso"
LOG_PATH="/home/rajames/PROJECTS/StarshipOS/starship_boot.log"

qemu-system-x86_64 -enable-kvm -boot d -cdrom "$ISO_PATH" -m 2048 \
-net nic -net user -serial stdio -cpu host \
-d cpu_reset,guest_errors,unimp,mmu \
> "$LOG_PATH" 2>&1
