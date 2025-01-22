#! /bin/sh

#
# StarshipOS Copyright (c) 2000-2025. R.A. James
#

javac -d . ../../../../../make/jdk/src/classes/build/tools/spp/Spp.java

gen() {
    out=Basic$2.java
    rm -f $out
    java build.tools.spp.Spp -K$1 -Dtype=$1 -DType=$2 -DFulltype=$3 -iBasic-X.java.template -o$out
}

gen byte Byte Byte
gen char Char Character
gen short Short Short
gen int Int Integer
gen long Long Long
gen float Float Float
gen double Double Double
