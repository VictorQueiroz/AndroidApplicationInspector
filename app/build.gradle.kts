plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  signingConfigs {
    create("release") {
    }
  }
  namespace = "com.jscriptlab.applicationinspector"
  compileSdk = 35

  packaging {
    resources {
      excludes += "META-INF/*"
      excludes += "xsd/*"
    }
  }

  defaultConfig {
    applicationId = "com.jscriptlab.applicationinspector"
    minSdk = 29
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  dependenciesInfo {
    includeInApk = true
    includeInBundle = true
  }
  ndkVersion = "27.2.12479018"
  buildToolsVersion = "35.0.0"
}

dependencies {
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.recyclerview)
  implementation(libs.google.material)
  implementation(libs.androidx.activity.ktx)
  implementation(libs.compose.theme.adapter.x)
  implementation(libs.com.android.lint.gradle.plugin)
  implementation(libs.jakarta.activation.api)
  testImplementation(libs.ext.junit)
}