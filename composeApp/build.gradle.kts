import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildKonfig)
}

fun getVersionCode(): Int {
    val code = providers.exec {
        commandLine("git", "rev-list", "--count", "HEAD")
    }.standardOutput.asText.get().trim()
    println("Version code: $code")
    return code.toIntOrNull() ?: 0
}

val appBuildFlavor = System.getenv("BUILD_FLAVOR")?.takeIf { it.isNotBlank() } ?: "x"
val appVersion = "1.0.${getVersionCode()}"

buildkonfig {
    packageName = "com.testdevlab.besttactoe"

    defaultConfigs {
        buildConfigField(STRING, "version", appVersion)
        buildConfigField(STRING, "flavor", appBuildFlavor)
        buildConfigField(STRING, "SOCKET_URL", "http://kokamocis.lv:404")
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.hotpreview)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.multiplatform.settings.serialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.socket.io.client)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.testdevlab.besttactoe"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.testdevlab.besttactoe"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.unit.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.compose.testing)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui.graphics.android)
    debugImplementation(compose.uiTooling)
    implementation(libs.coil.compose)

}

compose.desktop {
    application {
        mainClass = "com.testdevlab.besttactoe.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.testdevlab.besttactoe"
            packageVersion = appVersion

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            includeAllModules = true
            vendor = "TDL"
            description = "Best Tic Tac Toe"
            copyright = "© 2025 Adryum. All rights reserved."
            outputBaseDir.set(project.layout.buildDirectory.dir("release"))

            windows {
                dirChooser = true
                installationPath = "C:\\Program Files\\BestTacToe"
                upgradeUuid = "c8b9b4ef-02ea-4e0c-b4a2-0d6ebea83c69"
                shortcut = true
                iconFile.set(project.file("./src/commonMain/composeResources/files/app_icon.ico"))
            }
        }
    }
}
