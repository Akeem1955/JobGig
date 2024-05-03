plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.pioneers.jobgig"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pioneers.jobgig"
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-svg:2.5.0")
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
    implementation("androidx.media3:media3-common:1.3.1")
    implementation("androidx.compose.material:material-icons-extended")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.maps.android:maps-compose:4.3.2")

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation ("com.google.maps.android:maps-compose-utils:4.3.2")

    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation ("com.google.maps.android:maps-compose-widgets:4.3.2")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation ("com.google.maps.android:android-maps-utils:3.8.2")
    //implementation ("com.google.android.libraries.places:places:3.3.0")
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.datastore:datastore:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
//    implementation ("com.squareup.leakcanary:leakcanary-android:2.13")
    implementation ("io.getstream:stream-webrtc-android:1.1.1")
    implementation ("io.getstream:stream-webrtc-android-ktx:1.1.1")
    implementation ("io.getstream:stream-webrtc-android-compose:1.1.1")
    implementation ("io.getstream:stream-webrtc-android-ui:1.1.1")
    implementation("io.coil-kt:coil-video:2.5.0")
    implementation("io.getstream:stream-log:1.1.4")
    implementation("io.getstream:stream-log-android:1.1.4")
    implementation("com.google.ai.client.generativeai:generativeai:0.4.0")

}