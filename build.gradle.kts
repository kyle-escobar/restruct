plugins {
    kotlin("jvm")
    id("org.openjfx.javafxplugin")
    id("org.beryx.runtime")
    application
}

val gradleVersion: String by project
val javaVersion: String by project

tasks.wrapper {
    gradleVersion = this.gradleVersion
}

group = "dev.restruct"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.tinylog:tinylog-api-kotlin:_")
    implementation("org.tinylog:tinylog-impl:_")
    implementation("com.formdev:flatlaf:_")
    implementation("com.formdev:flatlaf-intellij-themes:_")
    implementation("no.tornado:tornadofx:_")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = javaVersion
        }
    }

    compileJava {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}

javafx {
    version = "17.0.1"
    modules = listOf("javafx.base", "javafx.controls", "javafx.graphics", "javafx.swing")
}

application {
    mainClass.set("dev.restruct.Restruct")
}
