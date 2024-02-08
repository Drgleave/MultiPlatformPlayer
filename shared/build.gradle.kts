
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.ui)
            implementation(libs.androidx.activity.compose)
            implementation(libs.decompose)

            api(libs.androidx.activity.compose)
            api(libs.androidx.appcompat)
            api(libs.androidx.core.ktx)
            implementation("androidx.media3:media3-exoplayer:1.2.1")

        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx)
            implementation(libs.moko.mvvm.compose)
            implementation(libs.kamel)

            implementation(libs.decompose)
            implementation(libs.decompose.jetbrains)

            implementation(libs.squareup.okio)
            implementation(libs.coroutines)

            implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")

            // Screen Model
            implementation("cafe.adriel.voyager:voyager-screenmodel:1.0.0")

            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.0.0")

            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:1.0.0")


        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.mpplayer"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }

}
