#!/bin/bash
#
# StarshipOS Copyright (c) 2022-2025. R.A. James
#

# Import common utils
. .github/scripts/report-utils.sh

GITHUB_STEP_SUMMARY="$1"
BUILD_DIR="$(ls -d build/*)"

# Send signal to the do-build action that we failed
touch "$BUILD_DIR/build-failure"

# Collect hs_errs for build-time crashes, e.g. javac, jmod, jlink, CDS.
# These usually land in make/
hs_err_files=$(ls make/hs_err*.log 2> /dev/null || true)

(
  echo '### :boom: Build failure summary'
  echo ''
  echo 'The build failed. Here follows the failure summary from the build.'
  echo '<details><summary><b>View build failure summary</b></summary>'
  echo ''
  echo '```'
  if [[ -f "$BUILD_DIR/make-support/failure-summary.log" ]]; then
    cat "$BUILD_DIR/make-support/failure-summary.log"
  else
    echo "Failure summary ($BUILD_DIR/make-support/failure-summary.log) not found"
  fi
  echo '```'
  echo '</details>'
  echo ''

  for hs_err in $hs_err_files; do
    echo "<details><summary><b>View HotSpot error log: "$hs_err"</b></summary>"
    echo ''
    echo '```'
    echo "$hs_err:"
    echo ''
    cat "$hs_err"
    echo '```'
    echo '</details>'
    echo ''
  done

  echo ''
  echo ':arrow_right: To see the entire test log, click the job in the list to the left. To download logs, see the `failure-logs` [artifact above](#artifacts).'
) >> $GITHUB_STEP_SUMMARY

truncate_summary
