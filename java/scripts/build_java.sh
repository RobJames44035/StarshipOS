#!/bin/sh
# shellcheck disable=SC2164
# shellcheck disable=SC2162
# shellcheck disable=SC2034
if [ ! -d build ]; then
  echo "Downloading JDK archive..."
  wget --progress=bar "https://download.java.net/java/GA/jdk23.0.1/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-23.0.1_linux-x64_bin.tar.gz"
  echo "Unpacking JDK archive..."
  tar -xzf openjdk-23.0.1_linux-x64_bin.tar.gz
  rm openjdk-23.0.1_linux-x64_bin.tar.gz
  cd ./jdk
  echo "Configuring build with boot JDK... $PWD"
  ./configure --with-boot-jdk="/home/rajames/PROJECTS/StarshipOS/java/jdk-23.0.1" --with-jobs=4 --with-debug-level=slowdebug
  echo "Cleaning previous builds if any..."
  make CONF="starship-os" clean
  echo "Building JDK..."
  make CONF="starship-os" hotspot
  make CONF="starship-os" -J 4

  rm -rfv ../jdk-23.0.1
fi

# Step 2: Unpack the archive if the directory does not exist
#if [ ! -d "$EXTRACTED_DIR" ]; then
#  echo "Unpacking JDK archive..."
#  mkdir -p $JDK_ARCHIVE
#  tar -xzfv "openjdk-23.0.1_linux-x64_bin.tar.gz"
#else
#  echo "JDK directory already exists."
#fi

# Configuration and build
#if [ ! -d build ]; then
#  echo "Configuring build with boot JDK..."
#  ./configure --with-boot-jdk="$BOOT_JDK"
#  cd ./jdk
#  echo "Cleaning previous builds if any..."
#  make clean
#  echo "Building JDK..."
#  make all
#echo "$PWD"
#echo "$CWD"
#read -p "This is a pause. Hit a key!"
#
##  echo "Copying build to ../build directory..."
##  cp ./jdk/build build
#fi

# Step 3: Clean up JDK archive and extracted directory
#echo "Cleaning up JDK archive and unpacked files..."
#rm -f "$JDK_ARCHIVE"
#rm -rf "$EXTRACTED_DIR"
echo "$PWD"
echo "$CWD"
# shellcheck disable=SC3045
read -p "This is a pause. Hit a key!"


echo "Build process complete."
