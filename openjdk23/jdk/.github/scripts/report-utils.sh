#!/bin/bash
#
# StarshipOS Copyright (c) 2024-2025. R.A. James
#

function truncate_summary() {
  # With large hs_errs, the summary can easily exceed 1024 kB, the limit set by Github
  # Trim it down if so.
  summary_size=$(wc -c < $GITHUB_STEP_SUMMARY)
  if [[ $summary_size -gt 1000000 ]]; then
    # Trim to below 1024 kB, and cut off after the last detail group
    head -c 1000000 $GITHUB_STEP_SUMMARY | tac | sed -n -e '/<\/details>/,$ p' | tac > $GITHUB_STEP_SUMMARY.tmp
    mv $GITHUB_STEP_SUMMARY.tmp $GITHUB_STEP_SUMMARY
    (
      echo ''
      echo ':x: **WARNING: Summary is too large and has been truncated.**'
      echo ''
    )  >> $GITHUB_STEP_SUMMARY
  fi
}
