// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false

}

buildscript {
    repositories {
        google() // Asegúrate de que esté aquí
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("com.android.tools.build:gradle:8.7.3")
    }
}