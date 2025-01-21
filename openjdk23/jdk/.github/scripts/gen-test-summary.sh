#!/bin/bash
#
# StarshipOS Copyright (c) 2022-2025. R.A. James
#

GITHUB_STEP_SUMMARY="$1"
GITHUB_OUTPUT="$2"

test_suite_name=$(cat build/run-test-prebuilt/test-support/test-last-ids.txt)
results_dir=build/run-test-prebuilt/test-results/$test_suite_name/text

if [[ ! -f build/run-test-prebuilt/make-support/exit-with-error ]]; then
  # There were no failures, exit now
  exit
fi

failures=$(sed -E -e 's/(.*)\.(java|sh)/\1/' -e '/^#/d' $results_dir/newfailures.txt 2> /dev/null || true)
errors=$(sed -E -e 's/(.*)\.(java|sh)/\1/' -e '/^#/d' $results_dir/other_errors.txt 2> /dev/null || true)
failure_count=$(echo $failures | wc -w || true)
error_count=$(echo $errors | wc -w || true)

if [[ "$failures" = "" && "$errors" = "" ]]; then
  # We know something went wrong, but not what
  echo 'failure=true' >> $GITHUB_OUTPUT
  echo 'error-message=Unspecified test suite failure. Please see log for job for details.' >> $GITHUB_OUTPUT
  exit 0
fi

echo 'failure=true' >> $GITHUB_OUTPUT
echo "error-message=Test run reported $failure_count test failure(s) and $error_count error(s). See summary for details." >> $GITHUB_OUTPUT

echo '### :boom: Test failures summary' >> $GITHUB_STEP_SUMMARY

if [[ "$failures" != "" ]]; then
  echo '' >> $GITHUB_STEP_SUMMARY
  echo 'These tests reported failure:' >> $GITHUB_STEP_SUMMARY
  for test in $failures; do
    anchor="$(echo "$test" | tr [A-Z/] [a-z_])"
    echo "* [$test](#user-content-$anchor)"
  done >> $GITHUB_STEP_SUMMARY
fi

if [[ "$errors" != "" ]]; then
  echo '' >> $GITHUB_STEP_SUMMARY
  echo 'These tests reported errors:'  >> $GITHUB_STEP_SUMMARY
  for test in $errors; do
    anchor="$(echo "$test" | tr [A-Z/] [a-z_])"
    echo "* [$test](#user-content-$anchor)"
  done >> $GITHUB_STEP_SUMMARY
fi
