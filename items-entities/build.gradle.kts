plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Api.compileSDK
    defaultConfig {
        minSdk = Api.minSDK
        targetSdk = Api.targetSDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        abortOnError = false
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(Libraries.kotlinJDK)

    implementation(project(":base"))

    implementation(Libraries.javaInject)

    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    kapt(Libraries.roomCompiler)

    implementation(Libraries.moshi)
    kapt(AnnotationProcessors.moshiCodegen)
}