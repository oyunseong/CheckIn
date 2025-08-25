import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.koin.core)


            // ✅ Decompose core (필수)
            implementation(libs.arkivanov.decompose.core)

            // ✅ Compose Multiplatform에서 Decompose 사용
            implementation(libs.arkivanov.decompose.extensions.compose)

//            implementation(libs.arkivanov.decompose.extensions.compose)
//            //arkivanov-decompose-jetpack-component-context
//            implementation(libs.arkivanov.decompose.jetpack.component.context)
            implementation(libs.arkivanov.essenty.lifecycle)
            implementation(libs.arkivanov.essenty.back.handler)
            implementation(libs.arkivanov.essenty.instance.keeper)
            implementation(libs.arkivanov.essenty.state.keeper)
            implementation(libs.compose.material.icons.extended)
        }

        androidMain.dependencies{


            // ✅ Android에서 Decompose + Jetpack Compose 연동
//            implementation(libs.arkivanov.decompose.extensions.compose.jetpack)

             implementation(libs.arkivanov.decompose.core)
//             implementation(libs.arkivanov.decompose.extensions.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "yun.checkin.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
