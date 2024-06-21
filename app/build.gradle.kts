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
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        mlModelBinding = true
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
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.tensorflow.lite.gpu)
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
    implementation (libs.tasks.vision)

    // CameraX core library
    implementation (libs.androidx.camera.core)

    // CameraX Camera2 extensions
    implementation (libs.androidx.camera.camera2)

    // CameraX View class
    implementation (libs.androidx.camera.view)

    // Tensorflow for Our Second ML Model
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
}
