#!/bin/sh
# The init script to set up system mounts, console, and start the jar.
# shellcheck disable=SC3045
#ulimit -c unlimited
# Step 1: Mount required filesystems
#echo "Mounting filesystems..." > /dev/console

#mount -t proc proc /proc || echo "Failed to mount /proc" > /dev/console
#mount -t sysfs sys /sys || echo "Failed to mount /sys" > /dev/console

# Skip mounting /dev if it's already mounted
#if ! mountpoint -q /dev; then
#    echo "/dev is not mounted. Mounting /dev..." > /dev/console
#    mount -t devtmpfs devtmpfs /dev || echo "Failed to mount /dev" > /dev/console
#else
#    echo "/dev is already mounted. Skipping..." > /dev/console
#fi

# Mount additional temporary filesystems
#mkdir -p /tmp /run
#mount -t tmpfs tmpfs /tmp || echo "Failed to mount /tmp" > /dev/console
#mount -t tmpfs tmpfs /run || echo "Failed to mount /run" > /dev/console

# Step 2: Create /dev/console if missing
if [ ! -e /dev/console ]; then
    echo "/dev/console missing, creating..." > /dev/console
    mknod /dev/console c 5 1
fi

# Step 3: Save random seed for entropy
echo "Saving random seed..." > /dev/console
RANDOM_SEED_FILE="/var/lib/starship/random-seed"
if [ -e "$RANDOM_SEED_FILE" ]; then
    cat "$RANDOM_SEED_FILE" > /dev/urandom
    echo "Loaded saved random seed." > /dev/console
fi

# Save new random seed on boot
dd if=/dev/urandom of="$RANDOM_SEED_FILE" bs=256 count=1 > /dev/null 2>&1 &

# Step 4: Start logging services (syslogd, klogd)
echo "Starting syslogd..." > /dev/console
syslogd || echo "Failed to start syslogd" > /dev/console

echo "Starting klogd..." > /dev/console
klogd || echo "Failed to start klogd" > /dev/console

# Step 5: Start system message bus (D-Bus)
echo "Starting system message bus..." > /dev/console
dbus-daemon --system || echo "Failed to start dbus-daemon" > /dev/console

# Step 6: Configure network (simplified for DHCP setup)
echo "Starting network..." > /dev/console
ifconfig eth0 up
udhcpc -i eth0 || echo "Network configuration failed." > /dev/console

# Optional: Start graphical environment
#if [ -e "/usr/bin/startx" ]; then
#    echo "Starting Xorg..." > /dev/console
#    startx || echo "Failed to start Xorg" > /dev/console
#fi

# Step 7: Start the init system
#echo "Starting the Java application..." > /dev/console
#exec java -Xmx2g -Xms1g -Xss32m -jar /var/lib/starship/init.jar || {
#    echo "Init.groovy failed, dropping to emergency shell." > /dev/console
#    exec /bin/sh
#}
echo "Running /sbin/init to launch Init main(){}"
ulimit -c unlimited
exec /sbin/init // start Init.groovy

