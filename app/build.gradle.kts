plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("de.undercouch.download") version "5.6.0"
}

android {
    namespace = "com.signify.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.signify.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Retrofit + Moshi + Interceptor
    implementation(libs.converter.moshi)
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.camera.lifecycle)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Coil
    implementation(libs.coil)

    // SplashScreen for Android 12 and up
    implementation(libs.androidx.core.splashscreen)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)

    // Testing-related
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Testing: Koin
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.koin.test.junit5)

    // Mediapipe Library
    implementation ("com.google.mediapipe:tasks-vision:0.20230731")

    // CameraX core library
    val cameraxVersion = "1.3.4"
    implementation ("androidx.camera:camera-core:$cameraxVersion")

    // CameraX Camera2 extensions
    implementation ("androidx.camera:camera-camera2:$cameraxVersion")

    // CameraX View class
    implementation ("androidx.camera:camera-view:$cameraxVersion")

    // WindowManager
    implementation ("androidx.window:window:1.1.0-alpha03")

}
