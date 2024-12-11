#!/bin/bash
clear
./mvnw install 2>&1 | tee maven.log
