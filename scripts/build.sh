#!/bin/bash
clear
mvn clean install 2>&1 | tee maven.log
