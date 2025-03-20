#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#
sudo -v
# So we can use this globally thus reducing typing errors.
export ROOT_FS="/mnt/rootfs/"

clear

# Function to display text
show_message() {
    if command -v figlet >/dev/null 2>&1; then
        # If figlet is installed
        figlet "$1"
    else
        # Fallback to plain text
        echo "$1"
    fi
}
show_message "Building & Running"
show_message "StarshipOS QEMU"
#########################################################
# Masking suspend to prevent interruption during build
echo "Disabling suspend, sleep, and hibernation..."
sudo systemctl mask sleep.target suspend.target hibernate.target hybrid-sleep.target
#########################################################

# Default to busybox=false if no argument is provided
BUSYBOX=$1

if [ -z "$BUSYBOX" ]; then
  echo "Building StarshipOS (default init)..."
  ./mvnw clean install
elif [ "$BUSYBOX" = "busybox" ]; then
  echo "Building StarshipOS with BusyBox..."
  ./mvnw -Dbusybox=true clean install
else
  echo "Invalid option: $BUSYBOX"
  echo "Usage: $0 [busybox]"
  exit 1
fi

#########################################################
# Unmask suspend settings after build is done
echo "Re-enabling suspend, sleep, and hibernation..."
sudo systemctl unmask sleep.target suspend.target hibernate.target hybrid-sleep.target
#########################################################

# Check if qemu is installed
if ! command -v qemu-system-x86_64 >/dev/null 2>&1; then
  echo "Error: QEMU is not installed. Please install QEMU to continue."
  exit 1
fi

# Run QEMU
echo "Running QEMU with StarshipOS..."
qemu-system-x86_64 -m 4096 -smp 2 -kernel buildroot/buildroot/output/images/bzImage \
  -drive file=buildroot/buildroot/output/images/rootfs.ext4,if=ide,format=raw \
  -netdev user,id=net0,hostfwd=tcp::5005-:5005 -device e1000,netdev=net0 \
  -append 'root=/dev/sda rw console=ttyS0 init=/sbin/init' -serial mon:stdio
