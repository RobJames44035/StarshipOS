#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
BUILD_DIR="$HOME/initramfs/build/init_ram_fs"
OUTPUT_IMAGE="initramfs.img"

# Paths to your components
BUSYBOX_SRC="$HOME/busybox/build/init_ram_fs"
GRUB_SRC="$HOME/grub/build/init_ram_fs"
KERNEL_SRC="$HOME/starship/build/init_ram_fs"

#JDK_SRC="/home/rajames/PROJECTS/StarshipOS/java/build/jdk/jdk"  # todo
#JDK_TARGET_DIR="$TARGET_DIR/usr/lib/jvm"  # Target directory for JDK inside the initramfs todo

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

function pause() {
  echo "$1"
  read -p "Paused... Hit [ENTER]"
}

log "Starting initramfs image creation script."log "Starting initramfs image creation script."


if [ ! -d "$BUILD_DIR" ]; then
    log "Creating target directory: $BUILD_DIR"
    mkdir -p "$BUILD_DIR"
    mkdir -p "$BUILD_DIR/boot/grub"

    log "Copying grub.cfg $BUILD_DIR/boot/grub"
    cp "$GRUB_SRC/boot/grub/grub.cfg" "$BUILD_DIR/boot/grub"

    log "Copying $BUSYBOX_SRC to target $BUILD_DIR"
    cp -rv "$BUSYBOX_SRC" "$HOME/initramfs/build"

    log "Copying the $KERNEL_SRC (starship) to target directory $HOME/initramfs/build."
    cp -rv "$KERNEL_SRC" "$HOME/initramfs/build"

    #++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    # Copy the JDK to the target directory
    log "Copying JDK to target directory."
    cp -rv $HOME/java/build/jdk/jdk/* $HOME/initramfs/target/initramfs_img

    #++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ++++++++++++++++++++++
    # Add any other content or files you want in your initramfs here
    # Make sure to include any required shared libraries, boot scripts, or configurations
    #++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    log "Adding init script."
    cat <<'EOF' > "$HOME/initramfs/build/init_ram_fs/linuxrc"
#!/bin/sh

echo "Starship getting ready for Warp."
# Mount the proc and sys filesystems
mount -t proc proc /proc
mount -t sysfs sys /sys

# Create device nodes
mknod -m 622 /dev/console c 5 1
mknod -m 666 /dev/null c 1 3

# Set JAVA_HOME for JDK
# export JAVA_HOME=/usr/lib/jvm/jdk
# export PATH=$JAVA_HOME/bin:$PATH

# Run BusyBox init
echo Engines started!
exec /bin/busybox init
EOF
    chmod +x "$HOME/initramfs/build/init_ram_fs/linuxrc"

    log "Ensuring /dev/null and /dev/console exist."
    mkdir -p "$HOME/initramfs/build/init_ram_fs/dev"
    sudo mknod -m 666 "$HOME/initramfs/build/init_ram_fs/dev/null" c 1 3
    sudo mknod -m 600 "$HOME/initramfs/build/init_ram_fs/dev/console" c 5 1

    log "Creating the initramfs image: $OUTPUT_IMAGE"
    (
        cd "$HOME/initramfs/build/init_ram_fs"
        find . -print0 | cpio --null --create --verbose --format=newc | gzip --best
    ) > "$HOME/initramfs/build/$OUTPUT_IMAGE"

    log "initramfs.img has been created successfully."
else
    log "Nothing to do.."
fi

log "Finished initramfs image creation script."
