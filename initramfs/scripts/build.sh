#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

if [ ! -d build ]; then
  # Function for timestamped logging
  function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
    #  read -p ""
  }

  log "Creating initrd build directory..."
  mkdir -p "build/initrd/boot/grub"

  # Copy necessary files
  log "Copying required files..."
  cp -r "target/kernel/build/init_ram_fs/boot" "build/initrd"
  cp -r "target/kernel/build/init_ram_fs/lib" "build/initrd"
  cp -r "target/busybox/build/init_ram_fs/bin" "build/initrd"
  cp -r "target/busybox/build/init_ram_fs/sbin" "build/initrd"
  cp "target/build/grub/hd0/init_ram_fs/boot/grub/grub.cfg" "build/initrd/boot/grub/grub.cfg"

  # Ensure init script exists and is executable
  log "Creating init script..."
  cat << 'EOF' > "build/initrd/init"
#!/bin/sh

# Mount the necessary filesystems
mount -t proc none /proc
mount -t sysfs none /sys
mount -t devtmpfs none /dev

# Print debug-friendly info
echo "**** Boot debugging shell ****"
echo "Mounting root filesystem on /mnt..."

# Attempt to mount the root filesystem
  mount -o ro /dev/sda3 /mnt || {
  echo "ERROR: Failed to mount the root filesystem on /mnt!"
  exec /bin/sh  # Drop to a debug shell if mount fails
}

# Switch to the new root filesystem
echo "Switching to the real root filesystem..."
exec switch_root /mnt /sbin/init
EOF

  sudo chmod +x "build/initrd/init"
  sudo chown root:root "build/initrd/init"
  log "Packaging initrd file..."
  cd "build/initrd"
  (
      find . -print0 | cpio --null -o -H newc | gzip > "$(realpath ../initrd.gz)"
  )

  log "Finished building initd.gz"
else
  log "Nothing to do."
fi
