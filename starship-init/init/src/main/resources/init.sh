#!/bin/sh
# The init script to set up system mounts, console, and start the jar

# Mount necessary filesystems
mount -t proc proc /proc
mount -t sysfs sys /sys
#mount -t devtmpfs dev /dev

# Create a console device if it doesn't exist
if [ ! -e /dev/console ]; then
    mknod /dev/console c 5 1
fi

# Redirect kernel messages to the console
echo "Booting..." > /dev/console
# shellcheck disable=SC3045
ulimit -s unlimited
# Start the Uberjar
echo "Starting Init..." > /dev/console
# shellcheck disable=SC2093
exec java -Xmx2g -Xms1g -Xss16m -XX:+PrintGCDetails -jar /var/lib/starship/init.jar || exec /bin/sh

#  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
# If exec fails, drop to a shell
echo "Exec failed, dropping to shell." > /dev/console
exec sh
