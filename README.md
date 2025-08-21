# tic-tac-toe-classic-128438-128447

This repository contains the Android frontend for a minimalistic Tic Tac Toe app.

Build and run:
- From the repo root (as most CI does):
  ./gradlew :app:assembleDebug

- From the android_frontend directory:
  ./gradlew :app:assembleDebug

Notes:
- A root-level gradlew delegates to android_frontend/gradlew to satisfy CI runners that invoke ./gradlew from the workspace root.
- Root-level settings.gradle includes the android_frontend project via includeBuild so tasks can be resolved from the repository root.
