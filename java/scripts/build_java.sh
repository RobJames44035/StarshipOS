#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162

# Define variables
BUILD_DIR="build"
JDK_ARCHIVE_URL="https://download.java.net/java/GA/jdk23.0.1/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-23.0.1_linux-x64_bin.tar.gz"
JDK_ARCHIVE_NAME="openjdk-23.0.1_linux-x64_bin.tar.gz"
JDK_DIR="jdk"
BOOT_JDK="/home/rajames/PROJECTS/StarshipOS/java/jdk-23.0.1"
PREFIX_DIR="/home/rajames/PROJECTS/StarshipOS/java/build/bin"
EXEC_PREFIX_DIR="/home/rajames/PROJECTS/StarshipOS/java/build/lib"

# Function to download and unpack JDK archive
download_and_unpack_jdk() {
    echo "Downloading JDK archive..."
    wget --progress=bar "$JDK_ARCHIVE_URL" -O "$JDK_ARCHIVE_NAME"
    echo "Unpacking JDK archive..."
    tar -xzf "$JDK_ARCHIVE_NAME"
    rm "$JDK_ARCHIVE_NAME"
}

# Function to configure and build the JDK
configure_and_build_jdk() {
    cd "./$JDK_DIR"
#    echo "Configuring build with boot JDK... $PWD"
    ./configure \
        --with-boot-jdk="$BOOT_JDK" \
        --with-jvm-variants=minimal \
        --enable-jit \
        --enable-libffi-bundling \
        --enable-hsdis-bundling \
        --with-jvm-features="compiler1,compiler2,zgc" \
        --enable-debug \
        --enable-full-docs \
        --enable-jvm-feature-cds \
        --enable-jvm-feature-dtrace \
        --prefix="$PREFIX_DIR" \
        --exec-prefix="$EXEC_PREFIX_DIR" \
        --verbose

    echo "Cleaning previous builds if any..."
    make CONF="linux-x86_64-server-release" clean
    echo "Building JDK..."
    make  CONF="linux-x86_64-server-release" images
cp /home/rajames/PROJECTS/StarshipOS/java/jdk/build/linux-x86_64-server-release/jdk $BUILD_DIR
    cd ../
}

# Main script logic
if [ ! -d "$BUILD_DIR" ]; then
    download_and_unpack_jdk
    configure_and_build_jdk
    echo "Removing BOOT_JDK directory..."
    rm -rf "$BOOT_JDK"
else
    echo "$BUILD_DIR already exists. Skipping build."
fi

echo "Build Java complete."
