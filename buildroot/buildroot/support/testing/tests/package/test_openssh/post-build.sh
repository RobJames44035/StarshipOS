#!/usr/bin/env bash

#
# StarshipOS Copyright (c) 2025. R.A. James
#

cat <<_EOF_ >>"${TARGET_DIR}/etc/ssh/sshd_config"
PermitRootLogin yes
PasswordAuthentication yes
_EOF_
