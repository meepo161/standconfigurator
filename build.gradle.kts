import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
//    id("org.jetbrains.compose") version "1.1.1"
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev741"
}

group = "ru.avem"
version = "0.0001a"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.components:components-splitpane:1.1.1")
    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc02")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0-rc02")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.1.1")
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
}

compose.desktop {
    application {
        mainClass = "ru.avem.standconfigurator.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "stand-configurator"
            packageVersion = "1.0.0"
        }
    }
}
