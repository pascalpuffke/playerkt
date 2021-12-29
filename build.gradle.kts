import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jetbrains.compose") version "1.0.1"
}

group = "de.pascalpuffke"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("net.jthink:jaudiotagger:3.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        jvmArgs += listOf("-ea", "-Xmx512M")

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Deb, TargetFormat.AppImage, TargetFormat.Exe)
            packageName = "playerkt"
            packageVersion = "1.0.0"
            description = "A miserable music player"
            copyright = "Copyright (c) 2021 Pascal Puffke"
            vendor = "Pascal Puffke"
            // This makes the installers show the entire GNU GPL license text as a EULA,
            // which feels weird and is kinda ridiculous, but I'll keep it
            licenseFile.set(project.file("LICENSE"))

            windows {
                dirChooser = true
            }

            linux {
                debMaintainer = "Pascal Puffke <pascal@pascalpuffke.de>"
            }

            macOS {
                signing {
                    // TODO
                }
            }
        }
    }
}