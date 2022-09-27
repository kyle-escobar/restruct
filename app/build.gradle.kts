plugins {
    application
    id("org.openjfx.javafxplugin")
}

dependencies {
    implementation(project(":natives"))
    implementation("org.tinylog:tinylog-api-kotlin:_")
    implementation("org.tinylog:tinylog-impl:_")
    implementation("no.tornado:tornadofx:_")
    implementation("com.formdev:flatlaf:_")
    implementation("com.formdev:flatlaf-intellij-themes:_")
    implementation("io.insert-koin:koin-core:_")
}

application {
    mainClass.set("dev.restruct.app.Restruct")
}

javafx {
    version = "11.0.2"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics", "javafx.base", "javafx.media", "javafx.fxml")
}