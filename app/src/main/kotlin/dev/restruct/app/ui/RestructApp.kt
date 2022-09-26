package dev.restruct.app.ui

import javafx.application.Platform
import javafx.scene.Scene
import tornadofx.*

class RestructApp : App(RootView::class) {

    lateinit var scene: Scene private set

    init {
        importStylesheet("/theme/restruct.css")
        Platform.setImplicitExit(false)
    }

    override fun shouldShowPrimaryStage(): Boolean = false

    override fun createPrimaryScene(view: UIComponent): Scene {
        scene = super.createPrimaryScene(view)
        return scene
    }
}