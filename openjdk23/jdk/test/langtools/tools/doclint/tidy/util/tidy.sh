#!/bin/bash
#
# StarshipOS Copyright (c) 2012-2025. R.A. James
#

# Run the "tidy" program over the files in a directory.
# The "tidy" program must be on your PATH.
#
# Usage:
#   sh tidy.sh <dir>
#
# The "tidy" program will be run on each HTML file in <dir>,
# and the output placed in the corresponding location in a new
# directory <dir>.tidy.  The console output from running "tidy" will
# be saved in a corresponding file with an additional .tidy extension.
#
# Non-HTML files will be copied without modification from <dir> to
# <dir>.tidy, so that relative links within the directory tree are
# unaffected.

dir=$1
odir=$dir.tidy

( cd $dir ; find . -type f ) | \
    while read file ; do
        mkdir -p $odir/$(dirname $file)
        case $file in
            *.html )
                cat $dir/$file | tidy 1>$odir/$file 2>$odir/$file.tidy
                ;;
            * ) cp $dir/$file $odir/$file
                ;;
        esac
    done
