#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

BUILD_DIR="build"
TARGET_DIR="target/initramfs_img"
OUTPUT_IMAGE="target/initramfs.img"
BUSYBOX_SRC="/home/rajames/PROJECTS/StarshipOS/busybox/build/*"

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting initramfs image creation script."

if [ ! -d "$BUILD_DIR" ]; then
    log "Creating target directory: $TARGET_DIR"
    mkdir -p "$TARGET_DIR"

    log "Copying BusyBox build content to target directory."
    cp -r $BUSYBOX_SRC "$TARGET_DIR"

    #++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    #             Copy any other content for the init ramdisk here
    #             Ensure to add any required shared libraries and boot scripts
    #++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    log "Adding init script."
    cat <<'EOF' > "$TARGET_DIR/init"
#!/bin/sh
# Mount the proc and sys filesystems
mount -t proc proc /proc
mount -t sysfs sys /sys

# Create device nodes
mknod -m 622 /dev/console c 5 1
mknod -m 666 /dev/null c 1 3

# Run BusyBox init
exec /bin/busybox init
EOF
    chmod +x "$TARGET_DIR/init"

    log "Ensuring /dev/null and /dev/console exist."
    mkdir -p "$TARGET_DIR/dev"
    sudo mknod -m 666 "$TARGET_DIR/dev/null" c 1 3
    sudo mknod -m 600 "$TARGET_DIR/dev/console" c 5 1

    log "Creating the initramfs image: $OUTPUT_IMAGE"
    (
        cd "$TARGET_DIR"
        find . -print0 | cpio --null --create --verbose --format=newc | gzip --best
    ) > "$OUTPUT_IMAGE"

    log "Copying initramfs image to build directory."
    mkdir -p "$BUILD_DIR"
    cp "$OUTPUT_IMAGE" "$BUILD_DIR"

    log "initramfs.img has been created successfully."
else
    log "Nothing to do.."
fi

log "Finished initramfs image creation script."
