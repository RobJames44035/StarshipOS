#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2046

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
BUSYBOX_SRC=$HOME/busybox/busybox
BUSYBOX_DST=$HOME/busybox/build/init_ram_fs

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting busybox build script"

if [ ! -d build ]; then
  log "Building BusyBox..."
  cd "$BUSYBOX_SRC"
  make clean
  make -j$(nproc) all

  log "installing Busybox..."
  mkdir -p "$BUSYBOX_DST"
  make CONFIG_PREFIX="$BUSYBOX_DST" install

make CONFIG_PREFIX="$BUSYBOX_DST" install

log "Replacing linuxrc with custom init script as a here document."
cd "../"
sudo rm "build/init_ram_fs/linuxrc"
sudo cat << 'EOF' > "build/init_ram_fs/linuxrc"
#!/bin/sh
echo "Initializing the system with custom linuxrc"

# Mount virtual filesystems
mount -t proc proc /proc
mount -t sysfs sys /sys
mount -t devtmpfs dev /dev

# Mount the actual root filesystem
mount -o rw /dev/sda3 /  # Replace /dev/xxx with your root device

# Switch to the real root filesystem
exec switch_root / /sbin/init

# If something goes wrong, drop into a shell
echo "Failed to switch to real root. Dropping to a shell."
exec /bin/sh
EOF

log "Set permissions for the new linuxrc script."
chmod +x "build/init_ram_fs/linuxrc"

  log "Setting proper file permissions & ownership for BusyBox."
  sudo chown root:root "build/init_ram_fs/linuxrc"
  sudo chmod 4755 "build/init_ram_fs/linuxrc"
else
  log "Nothing to do."
fi
