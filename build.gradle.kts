// ✅ Ovo mora biti prvo – buildscript za Firebase plugin
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}

// ✅ Pluginovi koje koristiš preko version catalog-a
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
