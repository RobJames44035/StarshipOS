#!/bin/bash

set -x
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

# Source the library file
source ../scripts/fs_library.sh
#
# Set versions and download URLs
#
INIT_VERSION="1.0.0"

JAVA_VERSION="23.0.2"
GRAAL_VERSION="jdk-23_linux-x64"
FELIX_VERSION="7.0.5"
ACTIVEMQ_VERSION="6.1.5"

JAVA_DOWNLOAD="https://download.java.net/java/GA/jdk${JAVA_VERSION}/6da2a6609d6e406f85c491fcb119101b/7/GPL/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
FELIX_DOWNLOAD="https://www.apache.org/dyn/closer.lua/felix/org.apache.felix.main.distribution-${FELIX_VERSION}.zip?action=download"
ACTIVEMQ_DOWNLOAD="https://www.apache.org/dyn/closer.cgi?filename=/activemq/${ACTIVEMQ_VERSION}/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz&action=download"
GRAAL_DOWNLOAD="https://download.oracle.com/graalvm/23/latest/graalvm-jdk-23_linux-x64_bin.tar.gz"

#
# The `make_dirs` function is designed to create necessary directories inside the mounted
# root filesystem.
#
function make_dirs() {
  log "Creating directories."
  mkdir -p "repo"
  sudo mkdir -p "/mnt/rootfs/java"
#  sudo mkdir -p "/mnt/rootfs/graal"
}

function starship() {
log "Installing libstarshipclib.so to /mnt/rootfs/java/lib"
sudo mkdir -p "/mnt/rootfs/java/lib"
sudo cp -v "../starship-sdk/target/lib/libstarshipclib.so" "/mnt/rootfs/java/lib" || exit

log "Installing init-${INIT_VERSION}.jar to /mnt/rootfs/var/lib/starship/init.jar"
sudo mkdir -p "/mnt/rootfs/var/lib/starship"
sudo cp -v "../starship-init/init/target/init-${INIT_VERSION}.jar" "/mnt/rootfs/var/lib/starship/init.jar" || exit

log "Installing default-init.groovy to /mnt/rootfs/etc/starship/config.d/init.groovy"
sudo mkdir -p "/mnt/rootfs/etc/starship/config.d"
sudo cp -v "../src/main/resources/default-init.groovy" "/mnt/rootfs/etc/starship/config.d/init.groovy" || exit

log "Installing osgi-manager-1.0.0.jar"
sudo cp -v "../starship-init/osgi-manager/target/osgi-manager-1.0.0.jar" "/mnt/rootfs/var/lib/starship/osgi-manager.jar" || exit
}

#
# The `jdk` function downloads the specified OpenJDK version for Linux, extracts it,
# and copies it to the mounted root filesystem.
#
function jdk() {
  log "#"
  log "# OpenJDK $JAVA_VERSION"
  log "#"
  # Check if the file already exists
  if [ ! -f "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" ]; then
    log "Downloading OpenJDK ${JAVA_VERSION}..."
    sudo wget "${JAVA_DOWNLOAD}" -O "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  else
    log "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  log "Extracting tarball"
  cd "./repo" || exit 1
  sudo tar xf "./openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  cd "../"
  log "Copy to root filesystem."
  sudo cp -rfv "./repo/jdk-${JAVA_VERSION}" "/mnt/rootfs/jdk-${JAVA_VERSION}"
  sudo rm -rf "/mnt/rootfs/java"
  sudo mv "/mnt/rootfs/jdk-${JAVA_VERSION}" "/mnt/rootfs/java"
  log "* Java JDK installed. *"
}

#
# The `graal` function downloads the specified GraalVM version for Linux, extracts it,
# and copies it to the mounted root filesystem.
#
function graal() {
  log "#"
  log "# GraalVM ${GRAAL_VERSION}"
  log "#"
  sudo rm -rf "/mnt/rootfs/graal"
  # Check if the file already exists
  if [ ! -f "repo/graalvm-jdk-23_linux-x64_bin.tar.gz" ]; then
    log "Downloading GraalVM 23..."
    sudo wget "${GRAAL_DOWNLOAD}" -O "repo/graalvm-jdk-23_linux-x64_bin.tar.gz"
  else
    log "repo/graalvm-${GRAAL_VERSION}_bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  log "Extracting tarball"
  cd "./repo" || exit 1
  sudo tar xvf "./graalvm-${GRAAL_VERSION}_bin.tar.gz"
  cd "../"
  log "Copy to root filesystem."
  sudo cp -rfv "./repo/graalvm-jdk-23.0.2+7.1" "/mnt/rootfs/graalvm-jdk-23.0.2+7.1"
  sudo rm -rf "/mnt/rootfs/graal"
  sudo mv "/mnt/rootfs/graalvm-jdk-23.0.2+7.1" "/mnt/rootfs/graal"

  log "GraalVM installed."
}

#
# The `felix` function downloads the specified Apache Felix framework and installs it
# in the correct location within the mounted root filesystem.
#
function felix() {
  log "#"
  log "# Apache Felix v${FELIX_VERSION}"
  log "#"
  sudo rm -rfv "/mnt/rootfs/opt/felix"
  # Check if the file already exists
  if [ ! -f "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip" ]; then
    log "Downloading Apache Felix v${FELIX_VERSION}..."
    sudo wget "${FELIX_DOWNLOAD}" -O "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  else
    log "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip already exists. Skipping download."
  fi

  sudo unzip -o "./repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  sudo mv -f "./felix-framework-7.0.5" "./repo"
  sudo cp -rfv "./repo/felix-framework-7.0.5" "/mnt/rootfs/opt/felix-framework-7.0.5"
  sudo mv "/mnt/rootfs/opt/felix-framework-7.0.5" "/mnt/rootfs/opt/felix"
  log "Felix framework installed."
}

#
# The `activemq` function downloads the specified Apache ActiveMQ version and installs it
# in the correct location within the mounted root filesystem.
#
function activemq() {
  log "#"
  log "# Apache ActiveMQ v${ACTIVEMQ_VERSION}"
  log "#"
  sudo rm -rfv "/mnt/rootfs/opt/activemq"
  # Check if the file already exists
  if [ ! -f "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz" ]; then
    log "Downloading Apache ActiveMQ v${ACTIVEMQ_VERSION}..."
    sudo wget "${ACTIVEMQ_DOWNLOAD}" -O "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  else
    log "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  sudo tar xvf "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  sudo mv -v "../binaries/apache-activemq-${ACTIVEMQ_VERSION}" "/mnt/rootfs/opt/apache-activemq-${ACTIVEMQ_VERSION}" || exit
  sudo mv -v "/mnt/rootfs/opt/apache-activemq-${ACTIVEMQ_VERSION}" "/mnt/rootfs/opt/activemq"
  log "ActiveMQ installed."
}

#
# The `copy_files` function copies specific files into the mounted root filesystem.
#
function copy_files() {
  log "Copying necessary files..."
  jdk
#  graal
  felix
  activemq
  starship
  echo "${MODE}"
  pause
if [ "${MODE}" = "starship" ]; then
  log "Install init C wrapper"
  sudo cp -v "../starship-init/init-c-wrapper/target/sbin-init" "/mnt/rootfs/sbin/init" || exit
  sudo rm -v "/mnt/rootfs/linuxrc"
fi
}

#
# The `cleanup_litter` function removes temporary files and directories created during the process.
#
function cleanup_litter() {
  log "Cleaning up."
  sudo rm -rf "./jdk-${JAVA_VERSION}"
  sudo rm -rf "./apache-activemq-${ACTIVEMQ_VERSION}"
  sudo rm -rf "./felix-framework-${FELIX_VERSION}"
  sudo rm -rf "./graalvm-jdk-23"
  sudo sync
}

#
# The `main` function orchestrates the process.
#
function main() {
  mount_rootfs
  make_dirs
  copy_files
  cleanup_litter
  unmount_rootfs
}
main
