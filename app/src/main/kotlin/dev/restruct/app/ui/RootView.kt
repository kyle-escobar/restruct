package dev.restruct.app.ui

import javafx.geometry.Orientation
import tornadofx.*

class RootView : View() {

    override val root = splitpane(orientation = Orientation.HORIZONTAL) {
        setDividerPositions(0.0)
        add<StructListView>()
        add<StructDataView>()
    }

}