plugins {
    // No plugins at the root; this file provides task aliases for composite builds.
}

// PUBLIC_INTERFACE
tasks.register("ciAssemble") {
    /** This task assembles the Android app debug build for CI composite delegation. */
    description = "Assemble Android app (debug) for CI"
    group = "build"
    dependsOn(":app:assembleDebug")
}

// PUBLIC_INTERFACE
tasks.register("ciCheck") {
    /** This task performs a lightweight verification for CI composite delegation. */
    description = "Run lightweight verification for CI"
    group = "verification"
    // In declarative samples, test tasks may not be present; ensure we at least build debug
    dependsOn(":app:assembleDebug")
}
