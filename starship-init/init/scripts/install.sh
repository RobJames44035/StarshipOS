#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

# Source the centralized library
source "../../scripts/fs_library.sh"

INIT_VERSION="1.0.0"
function make_dirs() {
  log "Creating directories."
  sudo mkdir -p "/mnt/rootfs/var/lib/starship/" "/mnt/rootfs/etc/starship/config.d"
}

function copy_files() {
  log "Copying files."
  sudo cp -v "target/init-${INIT_VERSION}.jar" "/mnt/rootfs/var/lib/starship/init.jar"
  sudo cp -v "src/main/resources/default-init.groovy" "/mnt/rootfs/etc/starship/config.d/init.groovy"
  if [ -e "/mnt/rootfs/linuxrc" ]; then
    sudo rm -v "/mnt/rootfs/linuxrc"
  fi
}

#function main() {
#  figlet "starship-init install"
#  read -p "x"
#  make_dirs
#  copy_files
#}
#
#main
