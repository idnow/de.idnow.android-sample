@file:Suppress("UnstableApiUsage")

buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("com.android.application")
    kotlin("android")

    id("com.jaredsburrows.license")
}

android {
    signingConfigs {
        create("config")
    }

    compileSdk = 32

    defaultConfig {
        applicationId = "de.idnow"
        minSdk = 21
        targetSdk = 32
        versionCode = 167
        versionName = "5.1.10"
        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
        }
        multiDexEnabled = true
    }

    buildTypes {
        release {
            // don"t use proguard, because there will be problems with the idnow SDK otherwise
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "IN_DEBUG_MODE", "false")
        }
        debug {
            isDebuggable = true
            // don"t use proguard
            isMinifyEnabled = false
            buildConfigField("boolean", "IN_DEBUG_MODE", "true")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources {
            excludes.add("META-INF/proguard/okhttp3.pro")
            excludes.add("META-INF/*.kotlin_module")
        }
    }
}

android {
    lint {
        abortOnError = false
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        de.idnow.sdk:idnow-android-sdk:5.1.12 has a transitive dependency on a library hosted in this repo, which we do not have access to.
//        maven {
//            url = uri("https://repo.authada.de/public/")
//            authentication {
//                create<BasicAuthentication>("basic")
//            }
//            credentials {
//                username = property("authada_username") as String
//                password = property("authada_password") as String
//            }
//        }
        maven {
            setUrl("https://raw.githubusercontent.com/idnow/de.idnow.android/master")
        }

        flatDir {
            dirs("libs") // this way we can find the .aar file in libs folder
        }
    }
}

dependencies {
    // Android
    implementation("androidx.multidex:multidex:2.0.1")

    // IDnow
    implementation("de.idnow.sdk:idnow-android-sdk:5.1.11")
//    This has a transitive dependency on a library hosted in this repo, which we do not have access to.
//    implementation("de.idnow.android.eid:idnow-android-eid-sdk:2.3.0")
}

android {
    packagingOptions {
        resources {
            excludes.add("META-INF/DEPENDENCIES.txt")
            excludes.add("META-INF/LICENSE.txt")
            excludes.add("META-INF/NOTICE.txt")
            excludes.add("META-INF/NOTICE")
            excludes.add("META-INF/LICENSE")
            excludes.add("META-INF/DEPENDENCIES")
            excludes.add("META-INF/notice.txt")
            excludes.add("META-INF/license.txt")
            excludes.add("META-INF/dependencies.txt")
            excludes.add("META-INF/LGPL2.1")
            excludes.add("META-INF/*.kotlin_module")
            excludes.add("META-INF/proguard/androidx-annotations.pro")
        }
    }
}
