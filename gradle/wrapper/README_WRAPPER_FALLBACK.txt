This project uses the android_frontend/gradle/wrapper JAR. If a CI runner requires a root-level wrapper JAR,
the root ./gradlew script will delegate to android_frontend/gradlew which uses its own wrapper JAR.
No additional binary is stored at the root to avoid duplication.
