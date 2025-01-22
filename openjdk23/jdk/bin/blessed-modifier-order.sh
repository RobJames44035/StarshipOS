#!/bin/bash
#
# StarshipOS Copyright (c) 2024-2025. R.A. James
#

usage() {
  (
    echo "$0 DIR ..."
    echo "Modifies in place all the java source files found"
    echo "in the given directories so that all java language modifiers"
    echo "are in the canonical order."
    echo "Tries to get it right even within javadoc comments,"
    echo "and even if the list of modifiers spans 2 lines."
    echo
    echo "See:"
    echo "https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.1.1"
    echo "https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.3.1"
    echo "https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4.3"
    echo "https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.8.3"
    echo "https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.1.1"
    echo "https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.4"
    echo
    echo "Example:"
    echo "$0 jdk/src/java.base jdk/test/java/{util,io,lang}"
  ) >&2
  exit 1
}

set -eu
declare -ar dirs=("$@")
[[ "${#dirs[@]}" > 0 ]] || usage
for dir in "${dirs[@]}"; do [[ -d "$dir" ]] || usage; done

declare -ar modifiers=(
  public protected private
  abstract default static final sealed non-sealed transient
  volatile synchronized native strictfp
)
declare -r SAVE_IFS="$IFS"
for ((i = 3; i < "${#modifiers[@]}"; i++)); do
  IFS='|'; x="${modifiers[*]:0:i}" y="${modifiers[*]:i}"; IFS="$SAVE_IFS"
  if [[ -n "$x" && -n "$y" ]]; then
    find "${dirs[@]}" -name '*.java' -type f -print0 | \
      xargs -0 perl -0777 -p -i -e \
      "do {} while s/^([A-Za-z@* ]*)\b($y)(\s|(?:\s|\n\s+\*)*\s)($x)\b/\1\4\3\2/mg"
  fi
done
