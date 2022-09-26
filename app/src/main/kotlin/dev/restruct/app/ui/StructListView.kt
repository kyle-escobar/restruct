package dev.restruct.app.ui

import tornadofx.View
import tornadofx.fitToParentSize
import tornadofx.treeview

class StructListView : View() {

    override val root = treeview<String> {
        fitToParentSize()
    }
}