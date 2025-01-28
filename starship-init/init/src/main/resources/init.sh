#!/bin/sh
# The init script to set up system mounts, console, and start the jar.

# Step 1: Mount required filesystems
echo "Mounting filesystems..." > /dev/console

mount -t proc proc /proc || echo "Failed to mount /proc" > /dev/console
mount -t sysfs sys /sys || echo "Failed to mount /sys" > /dev/console

# Skip mounting /dev if it's already mounted
if ! mountpoint -q /dev; then
    echo "/dev is not mounted. Mounting /dev..." > /dev/console
    mount -t devtmpfs devtmpfs /dev || echo "Failed to mount /dev" > /dev/console
else
    echo "/dev is already mounted. Skipping..." > /dev/console
fi

# Mount additional temporary filesystems
mkdir -p /tmp /run
mount -t tmpfs tmpfs /tmp || echo "Failed to mount /tmp" > /dev/console
mount -t tmpfs tmpfs /run || echo "Failed to mount /run" > /dev/console

# Step 2: Create /dev/console if missing
if [ ! -e /dev/console ]; then
    echo "/dev/console missing, creating..." > /dev/console
    mknod /dev/console c 5 1
fi

# Step 3: Set kernel logging and debugging options
echo "Booting Starship OS..." > /dev/console
echo "/tmp/core.%e.%p" > /proc/sys/kernel/core_pattern  # Enable core dumps
ulimit -s unlimited  # Avoid stack issues

# Step 4: Set PATH and LD_LIBRARY_PATH explicitly
export PATH=/bin:/sbin:/usr/bin:/usr/sbin
export LD_LIBRARY_PATH=/lib:/usr/lib

# Debugging variables (optional logs for confirmation)
echo "Environment verification:" > /dev/console
echo "PATH=$PATH" > /dev/console
echo "LD_LIBRARY_PATH=$LD_LIBRARY_PATH" > /dev/console

# Step 5: Start the Java application
echo "Starting the Java application..." > /dev/console

#exec java -Xmx2g -Xms1g -Xss32m \
#    -jar /var/lib/starship/init.jar || {
#        echo "Init.groovy failed, dropping to emergency shell." > /dev/console
#        exec /bin/jshell
#    }

# Fallback: Launch shell if all else fails
echo "Exec failed. Dropping to jshell." > /dev/console
exec /bin/jshell
