#! /bin/sh
#
# StarshipOS Copyright (c) 2025. R.A. James
#





COPYRIGHT_YEARS="$1"

cat <<__END__
/*
__END__

if [ "x$COPYRIGHT_YEARS" != x ]; then
  cat <<__END__
 * Copyright (c) $COPYRIGHT_YEARS Oracle and/or its affiliates. All rights reserved.
__END__
fi

$AWK ' /^#.*Copyright.*Oracle/ { next }
    /^#([^!]|$)/ { sub(/^#/, " *"); print }
    /^$/ { print " */"; exit } ' $0
