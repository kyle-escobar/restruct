package dev.restruct.app.ui

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme
import dev.restruct.app.ui.dialog.AttachProcessDialog
import dev.restruct.app.util.inject
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.FX
import tornadofx.Fragment
import tornadofx.find
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import kotlin.system.exitProcess

class Window {

    private val ui: UI by inject()

    var frame: JFrame

    init {
        JFrame.setDefaultLookAndFeelDecorated(true)
        JDialog.setDefaultLookAndFeelDecorated(true)
        FlatAtomOneDarkContrastIJTheme.setup()

        frame = JFrame("Restruct")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(1000, 650)
        frame.preferredSize = frame.size
        frame.layout = BorderLayout()
        frame.setLocationRelativeTo(null)
        frame.jMenuBar = frame.menubar()
        frame.iconImages = listOf(ImageIcon(Window::class.java.getResource("/images/restruct.png")).image)
    }

    fun open() {
        val fxPanel = JFXPanel()
        frame.add(fxPanel, BorderLayout.CENTER)
        frame.isVisible = true
        Platform.runLater {
            ui.app = UIApp()
            ui.app.start(Stage())
            fxPanel.scene = ui.app.scene
        }
    }

    fun close() {
        frame.isVisible = false
    }

    private fun JFrame.menubar() = menubar {
        menu("File") {
            item("Attach to Process") {
                onClick { openModal(find<AttachProcessDialog>()) }
            }
            separator()
            item("Exit") {
                onClick {
                    exitProcess(0)
                }
            }
        }
        menu("Edit")
        menu("View")
        menu("Tools")
        menu("Help)") {
            item("About")
        }
    }

    private fun openModal(view: Fragment) {
        val dialog = object : JDialog(frame) {
            init {
                size = Dimension(500, 400)
                preferredSize = size
                setLocationRelativeTo(null)
            }
        }
        val fxPanel = JFXPanel()
        dialog.contentPane.add(fxPanel)
        Platform.runLater {
            fxPanel.scene = Scene(view.root)
            FX.applyStylesheetsTo(fxPanel.scene)
            dialog.isVisible = true
        }
    }

    private fun menubar(block: JMenuBar.() -> Unit): JMenuBar {
        val menubar = JMenuBar()
        menubar.block()
        return menubar
    }

    private fun JMenuBar.menu(title: String, block: JMenu.() -> Unit = {}) {
        val menu = JMenu(title)
        menu.block()
        this.add(menu)
    }

    private fun JMenu.item(title: String, block: JMenuItem.() -> Unit = {}) {
        val item = JMenuItem(title)
        item.block()
        this.add(item)
    }

    private fun JMenu.separator() {
        val separator = JSeparator()
        this.add(separator)
    }

    private fun JMenuItem.onClick(block: () -> Unit) {
        this.addActionListener { block() }
    }
}