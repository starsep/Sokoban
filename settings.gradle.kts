import de.fayard.refreshVersions.bootstrapRefreshVersions

include(":app")

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.5")
}

bootstrapRefreshVersions()
