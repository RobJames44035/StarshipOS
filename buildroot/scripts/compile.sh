#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#
cp -pv scripts/default.config buildroot/.config
cd buildroot
make oldconfig
make all
cd ../
