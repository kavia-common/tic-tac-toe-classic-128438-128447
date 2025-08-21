#!/usr/bin/env bash
# Convenience wrapper ensuring CI can call gradlew from android_frontend root if needed.
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
exec "$SCRIPT_DIR/gradlew" "$@"
