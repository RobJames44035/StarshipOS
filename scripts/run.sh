#!/bin/sh

# Function to display the help message
show_help() {
    echo "Usage: $0 [--busybox | --starship] [threshold]"
    echo ""
    echo "Options:"
    echo "  --busybox   Build StarshipOS using BusyBox."
    echo "  --starship  Build StarshipOS with the default configuration."
    echo ""
    echo "Arguments:"
    echo "  threshold   Optional. Set the maximum run count before rebuilding (default: 30)."
    echo ""
    echo "Flags:"
    echo "  --help      Show this help message and exit."
    echo ""
    echo "Examples:"
    echo "  $0 --starship          Build StarshipOS with default settings."
    echo "  $0 --busybox           Build StarshipOS using BusyBox."
    echo "  $0 --starship 100      Run build with threshold set to 100."
    echo ""
}

# User-defined variables
DEFAULT_THRESHOLD=30
THRESHOLD=$DEFAULT_THRESHOLD
COUNTER_FILE=".run_counter"
IMAGES_DIR="buildroot/buildroot/output/images"
KERNEL_IMAGE="$IMAGES_DIR/bzImage"
ROOT_FS_IMAGE="./rootfs.ext4"

# Parse arguments
export MODE=""
if [ "$1" = "--help" ] || [ -z "$1" ]; then
    show_help
    exit 0
elif [ "$1" = "--busybox" ]; then
    MODE="busybox"
elif [ "$1" = "--starship" ]; then
    MODE="starship"
else
    echo "Error: Invalid mode. Use '--busybox' or '--starship'."
    show_help
    exit 1
fi

if [ -n "$2" ]; then
    if echo "$2" | grep -Eq '^[0-9]+$'; then
        THRESHOLD=$2
    else
        echo "Error: Threshold must be a numeric value (integer)."
        show_help
        exit 1
    fi
fi

# Initialize run counter if it does not exist
if [ ! -f "$COUNTER_FILE" ]; then
    echo "0" > "$COUNTER_FILE"
fi

# Increment and read the current run count
RUN_COUNT=$(cat "$COUNTER_FILE")
RUN_COUNT=$((RUN_COUNT + 1))
echo "$RUN_COUNT" > "$COUNTER_FILE"

# Handle threshold logic
if [ "$RUN_COUNT" -ge "$THRESHOLD" ]; then
    echo "###############################################"
    echo "# WARNING: Threshold reached ($RUN_COUNT runs)!"
    echo "# Cleaning and restarting the build process.   "
    echo "###############################################"
    echo ""
    echo "This will clean all outputs and reset the counter."
    echo "Do you want to proceed? (yes/no)"
    read -r CONFIRMATION
    if [ "$CONFIRMATION" != "yes" ]; then
        echo "Operation canceled. Exiting script."
        exit 0
    fi
    echo "Cleaning output directory..."
    if [ -d "$IMAGES_DIR" ]; then
        # shellcheck disable=SC2115
        rm -rf "${IMAGES_DIR}/*"
        echo "Output directory cleaned."
    else
        echo "Output directory not found, skipping cleanup."
    fi
    echo "Resetting the counter..."
    echo "0" > "$COUNTER_FILE"
    RUN_COUNT=0
fi

# Build logic
if [ "$MODE" = "starship" ]; then
    echo "Building StarshipOS with StarshipOS INIT..."
    ./mvnw clean install
elif [ "$MODE" = "busybox" ]; then
    echo "Building StarshipOS with BusyBox INIT..."
    ./mvnw -Dbusybox=true clean install
fi

# Ensure the build output exists
if [ ! -f "$KERNEL_IMAGE" ] || [ ! -f "$ROOT_FS_IMAGE" ]; then
    echo "Error: Required build artifacts are missing!"
    echo "Ensure the build system generated $KERNEL_IMAGE and $ROOT_FS_IMAGE."
    exit 1
fi
# Ensure prior mounts and loop devices are cleaned up
echo "Performing pre-launch cleanup..."

# Launch QEMU
echo "Launching QEMU to test the image..."
read -p "x"
qemu-system-x86_64 -m 4096 -smp 2 -kernel "$KERNEL_IMAGE" \
    -drive file="$ROOT_FS_IMAGE",if=ide,format=raw \
    -netdev user,id=net0,hostfwd=tcp::5005-:5005 -device e1000,netdev=net0 \
    -append 'root=/dev/sda rw console=ttyS0 init=/sbin/init' -serial mon:stdio