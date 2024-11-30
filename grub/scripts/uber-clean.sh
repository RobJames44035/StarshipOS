#!/bin/bash

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

rm -rfv build
