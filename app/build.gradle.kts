plugins {
    kotlin("kapt")
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id ("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
}


android {
    namespace = "com.example.foodrecipeapplicaiton"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodrecipeapplicaiton"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Compose dependencies
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coil Compose
    implementation(libs.coil.compose)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
//    implementation(libs.firebase.auth)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    //DataStore
    implementation (libs.androidx.datastore.preferences)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Lottie
    implementation(libs.lottie.compose)

    // Firebase
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.firestore.ktx)

    // Swipe Refresh
    implementation(libs.accompanist.swiperefresh)

    // Room
    implementation(libs.androidx.room.ktx)
//    kapt(libs.androidx.room.compiler)
    ksp("androidx.room:room-compiler:2.6.1")

    implementation (libs.androidx.room.runtime)



    implementation(libs.androidx.material.icons.extended)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.play.services.auth)

}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

hilt {
    enableTransformForLocalTests = true
}