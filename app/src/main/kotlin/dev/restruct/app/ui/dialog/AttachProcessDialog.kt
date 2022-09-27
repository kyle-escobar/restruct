package dev.restruct.app.ui.dialog

import dev.restruct.natives.ProcessList
import javafx.embed.swing.SwingFXUtils
import javafx.scene.text.Font
import tornadofx.*

class AttachProcessDialog : Fragment() {

    private val processList = ProcessList().asObservable()

    override val root = vbox {
        padding = insets(8.0)
        titledpane("Filter") {
            padding = insets(8.0)
            hbox {
                spacing = 16.0
                text("Process Name: ") {
                    font = Font.font(12.0)
                }
                textfield {
                    prefWidth = 400.0
                    maxWidth = 400.0
                    usePrefWidth = true
                }
            }
            button("Refresh")
        }

        tableview(processList) {
            readonlyColumn(" ", dev.restruct.natives.Process::icon) {
                cellFormat {
                    if(it != null) {
                        graphic = imageview(SwingFXUtils.toFXImage(it, null))
                    }
                }
            }
            readonlyColumn("PID", dev.restruct.natives.Process::id)
            readonlyColumn("Name", dev.restruct.natives.Process::name)
            readonlyColumn("Path", dev.restruct.natives.Process::path)
        }
    }

}