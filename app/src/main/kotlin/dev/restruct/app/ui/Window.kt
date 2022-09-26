package dev.restruct.app.ui

import dev.restruct.app.Restruct
import dev.restruct.app.util.inject
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.FX
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.SwingUtilities

class Window : JFrame("Restruct") {

    lateinit var app: RestructApp private set

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1000, 800)
        preferredSize = size
        minimumSize = Dimension(300, 300)
        layout = BorderLayout()
        setLocationRelativeTo(null)
        iconImage = ImageIcon(Window::class.java.getResource("/images/restruct.png")).image
        jMenuBar = MenuBar()
    }

    fun open() {
        SwingUtilities.invokeLater {
            val jfxPanel = JFXPanel()
            this.add(jfxPanel)

            Platform.runLater {
                app = RestructApp()
                val stage = Stage()
                app.start(stage)

                jfxPanel.scene = app.scene
                isVisible = true
            }
        }
    }

    fun close() {
        isVisible = false
    }

    inner class MenuBar : JMenuBar() {

        private val restruct: Restruct by inject()

        init {
            /*
             * File Menu
             */
            JMenu("File").also { menu ->
                JMenuItem("Close").also {
                    it.addActionListener { restruct.stop() }
                    menu.add(it)
                }
                add(menu)
            }

            /*
             * Edit Menu
             */
            JMenu("Edit").also { menu ->
                add(menu)
            }

            /*
             * View Menu
             */
            JMenu("View").also { menu ->
                add(menu)
            }

            /*
             * Tools Menu
             */
            JMenu("Tools").also { menu ->
                add(menu)
            }

            /*
             * Help Menu
             */
            JMenu("Help").also { menu ->
                JMenuItem("About").also {
                    menu.add(it)
                }
                add(menu)
            }
        }
    }
}