#!/bin/sh
set -euo pipefail

figlet "StarshipOS_fiasco"
echo "Executing compile.sh"

make B=target
cd target
make
cd ..
