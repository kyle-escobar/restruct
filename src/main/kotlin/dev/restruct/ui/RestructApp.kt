package dev.restruct.ui

import javafx.application.Application
import javafx.stage.Stage
import tornadofx.FX

class RestructApp : Application() {

    override fun start(stage: Stage) {
        FX.registerApplication(this, stage)
    }

}