#! /bin/sh

#
# StarshipOS Copyright (c) 2003-2025. R.A. James
#

javac -d . ../../../../../make/jdk/src/classes/build/tools/spp/Spp.java

gen() {
#  if [ $3 = "true" ]
#  then $SPP -K$1 -Dtype=$1 -DType=$2 -Kprim<Basic-X.java.template >Basic$2.java
#  else $SPP -K$1 -Dtype=$1 -DType=$2 -K$3 <Basic-X.java.template >Basic$2.java
#  fi
    out=Basic$2.java
    rm -f $out
    java build.tools.spp.Spp -K$1 -Dtype=$1 -DType=$2 -K$3 -K$4 -K$5 -K$6 -iBasic-X.java.template -nel -o$out
}

gen boolean Boolean       prim  ""  ""   ""
gen Boolean BooleanObject ""    ""  ""   ""
gen byte Byte             prim  ""  dec  ""
gen Byte ByteObject       ""    ""  dec  ""
gen char Char             prim  ""  ""   ""
gen Character CharObject  ""    ""  ""   ""
gen short Short           prim  ""  dec  ""
gen Short ShortObject     ""    ""  dec  ""
gen int Int               prim  ""  dec  ""
gen Integer IntObject     ""    ""  dec  ""
gen long Long             prim  ""  dec  ""
gen Long LongObject       ""    ""  dec  ""
gen BigInteger BigInteger ""    ""  ""   ""

gen float Float           prim  fp  ""   ""
gen Float FloatObject     ""    fp  ""   ""
gen double Double         prim  fp  ""   ""
gen Double DoubleObject   ""    fp  ""   ""
gen BigDecimal BigDecimal ""    fp  ""   ""

gen Calendar DateTime     ""    ""  ""   datetime

rm -rf build
