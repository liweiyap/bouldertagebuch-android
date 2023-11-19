// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply{
        set("kotlinVersion", "1.9.10")
        set("composeUIVersion", "1.5.4")
        set("lifecycleVersion", "2.6.2")
        set("hiltVersion", "2.48.1")
    }
}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}