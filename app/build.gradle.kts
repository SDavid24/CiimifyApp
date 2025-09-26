import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)

    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")

}

android {
    namespace = "com.newagedavid.climifyapp"
    compileSdk = 36

    android.buildFeatures.buildConfig = true

    val localProps = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }
    val openWeatherAiApiKey: String = localProps.getProperty("OPEN_WEATHER_API_KEY") ?: ""


    defaultConfig {
        applicationId = "com.newagedavid.climifyapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"$openWeatherAiApiKey\"")
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
        compose = true
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
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.ads.mobile.sdk)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.places)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Coil for Compose image loading
    implementation (libs.coil.compose)

    ///horizontal screen pager
    implementation (libs.androidx.foundation)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // Koin for DI
    implementation (libs.koin.android)
    implementation (libs.koin.androidx.compose)

    // Retrofit + OkHttp + Moshi
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.logging.interceptor)
    implementation (libs.moshi)
    ksp (libs.moshi.kotlin.codegen)
    implementation(libs.moshi.kotlin)

    implementation(libs.androidx.material.icons.extended)


    // lottie animations
    implementation(libs.lottie.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Timber (logging)
    implementation(libs.timber)

}