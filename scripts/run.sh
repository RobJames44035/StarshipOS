#!/bin/sh
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

clear
figlet "Building & Running"
figlet "StarshipOS QEMU"

#!/bin/sh
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

# Run QEMU
qemu-system-x86_64 -m 4096 -smp 2 -kernel buildroot/buildroot/output/images/bzImage \
  -drive file=buildroot/buildroot/output/images/rootfs.ext4,if=ide,format=raw \
  -netdev user,id=net0,hostfwd=tcp::5005-:5005 -device e1000,netdev=net0 \
  -append 'root=/dev/sda rw console=ttyS0 init=/sbin/init' -serial mon:stdio
