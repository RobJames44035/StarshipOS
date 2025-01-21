#!/bin/bash
#
# StarshipOS Copyright (c) 2012-2025. R.A. James
#

GREP=grep

#
EXP="Note: Some input files use or override a deprecated API."
EXP="${EXP}|Note: Recompile with -Xlint:deprecation for details."
EXP="${EXP}|Note: Some input files use unchecked or unsafe operations."
EXP="${EXP}|Note: Recompile with -Xlint:unchecked for details."
EXP="${EXP}| warning"
EXP="${EXP}|uses or overrides a deprecated API."
EXP="${EXP}|uses unchecked or unsafe operations."
#
${GREP} --line-buffered -v -E "${EXP}"
