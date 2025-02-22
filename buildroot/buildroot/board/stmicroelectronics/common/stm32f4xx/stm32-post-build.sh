#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

# Busybox is built without network support
sed -i '/hostname/d' ${TARGET_DIR}/etc/inittab

# Kernel is built without devpts support
sed -i '/^devpts/d' ${TARGET_DIR}/etc/fstab
