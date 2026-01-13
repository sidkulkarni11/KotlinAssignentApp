plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt") // ✅ REQUIRED for Room
}

android {
    namespace = "com.sid.kotlinassignentapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sid.kotlinassignentapp"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // -------------------- EXISTING CORE --------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.fragment:fragment-ktx:1.7.1")

    // -------------------- MVVM --------------------
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

    // -------------------- RecyclerView --------------------
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // -------------------- Coroutines --------------------
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // -------------------- Retrofit --------------------
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // -------------------- OkHttp --------------------
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // -------------------- Room (Offline Cache) --------------------
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    // -------------------- Paging 3 --------------------
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")

    // Swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // -------------------- DataStore (Settings) --------------------
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // -------------------- WorkManager --------------------
    implementation("androidx.work:work-runtime-ktx:2.9.0")



    // -------------------- Testing --------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
