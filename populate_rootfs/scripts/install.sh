#!/bin/bash

source "../scripts/fs_library.sh"
INIT_VERSION="1.0.0"
mount_rootfs
pause "populate /mnt/rootfs: [ENTER] to start install."

log "Installing libstarshipclib.so to /mnt/rootfs/java/lib"
sudo mkdir -p "/mnt/rootfs/java/lib"
sudo cp -v "./target/lib/libstarshipclib.so" "/mnt/rootfs/java/lib"

log "Installing init-${INIT_VERSION}.jar to /mnt/rootfs/var/lib/starship/init.jar"
sudo cp -v "../starship-init/init/target/init-${INIT_VERSION}.jar" "/mnt/rootfs/var/lib/starship/init.jar"

log "Installing default-init.groovy to /mnt/rootfs/etc/starship/config.d/init.groovy"
sudo cp -v "src/main/resources/default-init.groovy" "/mnt/rootfs/etc/starship/config.d/init.groovy"

log "Installing osgi-manager-1.0.0.jar"
sudo cp -v "../starship-init/osgi-manager/target/osgi-manager-1.0.0.jar" "/mnt/rootfs/var/lib/starship/osgi-manager.jar"

pause "$1"
log "Install init C wrapper"
sudo cp -v "../starship-init/init-c-wrapper/target/sbin-init" "/mnt/rootfs/sbin/init"

if [ -e "/mnt/rootfs/linuxrc" ]; then
  sudo rm -v "/mnt/rootfs/linuxrc"
fi

pause "[ENTER] to finish install"
umount_rootfs