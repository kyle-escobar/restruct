package dev.restruct.ui

import dev.restruct.ui.view.RootView
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
import kotlin.system.exitProcess

class RestructFrame : JFrame("Restruct") {

    private val jfxPanel = JFXPanel()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1080, 800)
        preferredSize = size
        minimumSize = Dimension(500, 500)
        iconImages = ICONS
        jMenuBar = MENU_BAR
        layout = BorderLayout()
        setLocationRelativeTo(null)
    }

    private fun init() {
        val view = FX.find<RootView>().root
        jfxPanel.scene = Scene(view)
        add(jfxPanel, BorderLayout.CENTER)

        Platform.setImplicitExit(false)

        Platform.runLater {
            val stage = Stage()
            RestructUI.app = RestructApp()
            RestructUI.app.start(stage)
        }
    }

    fun open() {
        init()
        isVisible = true
    }

    fun close() {
        isVisible = false
    }

    companion object {

        private val ICONS = listOf(
            "icon-32.png",
            "icon-64.png",
            "icon-128.png",
            "icon-256.png"
        ).map { ImageIcon(RestructFrame::class.java.getResource("/images/icons/$it")).image }

        private val MENU_BAR = JMenuBar().also { menubar ->
            JMenu("File").also { menu ->
                JMenuItem("Exit").also { item ->
                    item.addActionListener { exitProcess(0) }
                    menu.add(item)
                }
                menubar.add(menu)
            }

            JMenu("View").also { menu ->
                menubar.add(menu)
            }

            JMenu("Structures").also { menu ->
                menubar.add(menu)
            }

            JMenu("Help").also { menu ->
                JMenuItem("About").also { item ->
                    menu.add(item)
                }
                menubar.add(menu)
            }
        }

    }
}