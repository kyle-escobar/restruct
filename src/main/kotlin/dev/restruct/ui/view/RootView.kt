package dev.restruct.ui.view

import tornadofx.View
import tornadofx.text
import tornadofx.vbox

class RootView : View() {

    override val root = vbox {
        text("Hello World!")
    }

}