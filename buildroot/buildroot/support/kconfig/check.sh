#!/bin/sh
#
# StarshipOS Copyright (c) 2025. R.A. James
#

# SPDX-License-Identifier: GPL-2.0
# Needed for systems without gettext
$* -x c -o /dev/null - > /dev/null 2>&1 << EOF
#include <libintl.h>
int main()
{
	gettext("");
	return 0;
}
EOF
if [ ! "$?" -eq "0"  ]; then
	echo -DKBUILD_NO_NLS;
fi
