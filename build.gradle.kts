// ✅ Firebase i ostali classpath pluginovi
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}

// ✅ Pluginovi korišteni u modulima (via version catalog)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms) apply false // ako si ga definirao u toml-u
}
