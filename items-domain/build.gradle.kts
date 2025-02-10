plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("jacoco")
    id("plugins.jacoco-report")
  kotlin("plugin.serialization")
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

    tasks.withType<Test> {
        useJUnitPlatform()
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
    implementation(project(":core"))
    implementation(project(":items-entities"))

    implementation(Libraries.paging)

    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomPaging)
    kapt(Libraries.roomCompiler)

    implementation(AnnotationProcessors.daggerHilt)
    kapt(AnnotationProcessors.daggerHiltAndroidCompiler)
    implementation(AnnotationProcessors.daggerHiltViewModel)

    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitMoshi)

  implementation(Libraries.jsonSerialization)

  Libraries.suiteTest.forEach { testImplementation(it) }
}

afterEvaluate {
    val function = extra.get("generateCheckCoverageTasks") as (File, String, Coverage, List<String>, List<String>) -> Unit
    function.invoke(
        buildDir,
        "test",
        Coverage(
            instructions = 100.0,
            lines = 100.0,
            complexity = 100.0,
            methods = 100.0,
            classes = 100.0
        ),
        emptyList(),
        emptyList()
    )
}
