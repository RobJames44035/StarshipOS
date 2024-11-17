#!/bin/bash
# shellcheck disable=SC2164
if [ ! -d build ]; then
    mkdir -p target/src
    cd target/src
    wget https://busybox.net/downloads/busybox-1.35.0.tar.bz2
    tar -xjvf busybox-1.35.0.tar.bz2
    cd ./busybox-1.35.0
    make defconfig
    make
    make CONFIG_PREFIX=../../../build install
    sudo chown root:root ../../../build/bin/busybox
    sudo chmod 4755 ../../../build/bin/busybox
fi
