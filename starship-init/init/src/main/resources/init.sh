#!/bin/sh
# The init script to set up system mounts, console, and start the jar

# Mount necessary filesystems
mount -t proc proc /proc
mount -t sysfs sys /sys
mount -t devtmpfs dev /dev

# Create a console device if it doesn't exist
if [ ! -e /dev/console ]; then
    mknod /dev/console c 5 1
fi

# Redirect kernel messages to the console
echo "Booting..." > /dev/console

# Start the Uberjar
echo "Starting init jar..." > /dev/console
# shellcheck disable=SC2093
exec java -jar /var/lib/starship/init-0.1.0-SNAPSHOT.jar

# If exec fails, drop to a shell
echo "Exec failed, dropping to shell." > /dev/console
exec sh
