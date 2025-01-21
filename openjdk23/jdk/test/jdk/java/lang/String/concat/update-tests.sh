#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

#!/bin/bash

javac ImplicitStringConcatShapesTestGen.java
java ImplicitStringConcatShapesTestGen > ImplicitStringConcatShapes.java
rm ImplicitStringConcatShapesTestGen.class
