package dev.restruct.app.ui

import javafx.scene.Parent
import tornadofx.View
import tornadofx.fitToParentSize
import tornadofx.treetableview

class StructDataView : View() {

    override val root = treetableview<String> {
        fitToParentSize()
    }
}