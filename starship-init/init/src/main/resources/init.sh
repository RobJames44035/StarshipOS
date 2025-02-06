#!/bin/sh
# The init script to set up system mounts, console, and start the jar.
# shellcheck disable=SC3045
#ulimit -c unlimited

# Step 2: Create /dev/console if missing
#if [ ! -e /dev/console ]; then
#    echo "/dev/console missing, creating..." > /dev/console
#    mknod /dev/console c 5 1
#fi

# Step 3: Save random seed for entropy
#echo "Saving random seed..." > /dev/console
#RANDOM_SEED_FILE="/var/lib/starship/random-seed"
#if [ -e "$RANDOM_SEED_FILE" ]; then
#    cat "$RANDOM_SEED_FILE" > /dev/urandom
#    echo "Loaded saved random seed." > /dev/console
#fi

# Save new random seed on boot
#dd if=/dev/urandom of="$RANDOM_SEED_FILE" bs=256 count=1 > /dev/null 2>&1 &

# Step 4: Start logging services (syslogd, klogd)
#echo "Starting syslogd..." > /dev/console
#syslogd || echo "Failed to start syslogd" > /dev/console

#echo "Starting klogd..." > /dev/console
#klogd || echo "Failed to start klogd" > /dev/console

# Step 5: Start system message bus (D-Bus)
#echo "Starting system message bus..." > /dev/console
#rm /run/messagebus.pid
#dbus-daemon --system || echo "Failed to start dbus-daemon" > /dev/console

# Step 6: Configure network (simplified for DHCP setup)
#echo "Starting network..." > /dev/console
#ifconfig eth0 up
#udhcpc -i eth0 || echo "Network configuration failed." > /dev/console

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
#exec /sbin/init # start Init.groovy

