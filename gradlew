#!/bin/sh
# Portable POSIX wrapper to delegate to android_frontend/gradlew.
# Some CI runners execute from the repo root and require ./gradlew to exist here.

set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
PROJECT_WRAPPER="$SCRIPT_DIR/android_frontend/gradlew"

if [ ! -x "$PROJECT_WRAPPER" ]; then
  echo "Gradle wrapper not found or not executable at $PROJECT_WRAPPER" >&2
  exit 127
fi

exec "$PROJECT_WRAPPER" "$@"
