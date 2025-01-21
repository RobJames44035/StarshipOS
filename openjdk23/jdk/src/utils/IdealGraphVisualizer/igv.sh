#!/bin/sh
#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

mvn --batch-mode -f application/pom.xml nbm:run-platform -Dnetbeans.run.params="-J-da" >.igv.log 2>&1
