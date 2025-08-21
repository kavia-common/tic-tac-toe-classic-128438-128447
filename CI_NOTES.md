If your CI runner reports: "./gradlew: No such file or directory" or "Permission denied", ensure wrapper scripts are executable.

Add a step before running Gradle:
  chmod +x ./gradlew ./android_frontend/gradlew

Then run:
  ./gradlew :app:assembleDebug
