#!/bin/sh

# shellcheck disable=SC3040
set -euo pipefail

figlet "StarshipOS_fiasco Clean"

if [ -d target ]; then
  rm -rf ./target
fi