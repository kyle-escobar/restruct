plugins {
    kotlin("jvm")
}

tasks.wrapper {
    gradleVersion = "7.4.2"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "dev.restruct"
    version = "0.1.0"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("reflect"))
    }

    tasks.compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}