package dev.restruct.app.ui

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme
import javax.swing.JDialog
import javax.swing.JFrame

class UI {

    lateinit var window: Window private set

    fun open() {
        /*
         * Setup Java swing theme.
         */
        JFrame.setDefaultLookAndFeelDecorated(true)
        JDialog.setDefaultLookAndFeelDecorated(true)
        FlatAtomOneDarkContrastIJTheme.setup()

        /*
         * Open Window.
         */
        window = Window()
        window.open()
    }

    fun close() {
        window.close()
    }
}