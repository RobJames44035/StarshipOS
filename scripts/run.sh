#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

ISO_PATH=/home/rajames/PROJECTS/StarshipOS/StarshipOS.iso
LOG_PATH="/home/rajames/PROJECTS/StarshipOS/starship_boot.log"

qemu-system-x86_64 -boot d -cdrom "$ISO_PATH" -m 2048 \
-net nic -net user -serial stdio -cpu qemu64 -object memory-backend-ram,id=ram1,size=2048M  \
-numa node,memdev=ram1 -d int,cpu_reset,guest_errors -mem-path /dev/hugepages \
> "$LOG_PATH" 2>&1
