package dev.restruct.app.ui

import dev.restruct.app.ui.view.RootView
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.App
import tornadofx.UIComponent

class UIApp : App(RootView::class) {

    lateinit var stage: Stage
    lateinit var scene: Scene

    override fun start(stage: Stage) {
        this.stage = stage
        super.start(stage)
    }

    override fun createPrimaryScene(view: UIComponent): Scene {
        this.scene = super.createPrimaryScene(view)
        return this.scene
    }

    override fun shouldShowPrimaryStage(): Boolean = false

}