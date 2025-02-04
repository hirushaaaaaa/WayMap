val gms: Any
    get() {
        TODO()
    }

plugins {
    alias(libs.plugins.android.application)
    id ("com.google.gms.google-services")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)

}

android {
    namespace = "com.example.waymap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.waymap"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}



dependencies {


    // Existing dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gridlayout)
    implementation(libs.play.services.ads)
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation(libs.firebase.database)
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation ("com.google.android.gms:play-services-maps:18.1.0")

    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.auth)
    implementation ("com.google.maps.android:android-maps-utils:2.3.0")
    implementation ("com.google.maps:google-maps-services:2.1.2")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps:google-maps-services:2.1.2")
    implementation("com.google.maps.android:android-maps-utils:2.3.0")

    // Required for Google Maps Services
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("com.google.code.gson:gson:2.10")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    implementation ("com.google.maps.android:android-maps-utils:2.3.0")
    implementation ("com.google.maps:google-maps-services:2.1.2")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps:google-maps-services:2.1.2")
    implementation("com.google.maps.android:android-maps-utils:2.3.0")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("com.google.code.gson:gson:2.10")



    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}
