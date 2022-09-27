package dev.restruct.app.ui.dialog

import dev.restruct.natives.Process
import dev.restruct.natives.ProcessList
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.embed.swing.SwingFXUtils
import javafx.scene.text.Font
import org.tinylog.kotlin.Logger
import tornadofx.*

class AttachProcessDialog : Fragment() {

    val data: ProcessDialogData by inject()

    var processesCache = mutableListOf<Process>()

    override val root = vbox {
        title = "Attach to Process"
        padding = insets(16.0)
        spacing = 16.0
        titledpane("Filter") {
            padding = insets(8.0)
            vbox {
                spacing = 8.0
                hbox {
                    spacing = 16.0
                    text("Process Name: ") {
                        font = Font.font(12.0)
                    }
                    textfield(data.processNameFilterProperty) {
                        prefWidth = 275.0
                        maxWidth = prefWidth
                        usePrefWidth = true
                    }
                }
                button("Refresh") {
                    action { refreshProcessList() }
                }
            }
        }

        refreshProcessList()
        tableview(data.processes) {
            readonlyColumn(" ", Process::icon) {
                cellFormat {
                    text = " "
                    if(it != null) {
                        graphic = imageview(SwingFXUtils.toFXImage(it, null))
                    }
                }
            }.contentWidth(useAsMax = true)
            readonlyColumn("PID", Process::id).contentWidth(useAsMax = true)
            readonlyColumn("Name", Process::name).contentWidth(useAsMax = true)
            readonlyColumn("Path", Process::path).contentWidth(useAsMax = true, useAsMin = true)
            bindSelected(data.selectedProcessProperty)
        }

        button("Attach") {
            fitToParentWidth()
            action { attach() }
        }
    }

    private fun refreshProcessList() {
        data.processes.clear()
        data.processes.addAll(ProcessList())
        data.commit()
        processesCache = data.processes
    }

    private fun filterProcessList() {
        data.processes.clear()
        if(data.processNameFilter.isBlank()) {
            data.processes.addAll(processesCache)
        } else {
            data.processes.removeIf { it.name.contains(data.processNameFilter) }
        }
        data.processes.sortBy { it.name }
        data.processes.sortBy { it.name.startsWith(data.processNameFilter) }
    }

    private fun attach() {
        Logger.info("Attaching to process: ${data.selectedProcess.name} (PID: ${data.selectedProcess.id}).")
        data.rollback()
    }

    init {
        data.processNameFilterProperty.onChange { filterProcessList() }
    }
}

class ProcessDialogData : ViewModel() {

    val processesProperty = SimpleObjectProperty<ObservableList<Process>>()
    var processes by processesProperty

    val selectedProcessProperty = SimpleObjectProperty<Process>()
    var selectedProcess by selectedProcessProperty

    val processNameFilterProperty = SimpleStringProperty()
    var processNameFilter by processNameFilterProperty

    init {
        processes = mutableListOf<Process>().asObservable()
    }
}