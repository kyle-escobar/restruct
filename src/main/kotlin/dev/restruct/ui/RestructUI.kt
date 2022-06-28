package dev.restruct.ui

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme
import javax.swing.JDialog
import javax.swing.JFrame

object RestructUI {

    lateinit var frame: RestructFrame
    lateinit var app: RestructApp

    private fun init() {
        JFrame.setDefaultLookAndFeelDecorated(true)
        JDialog.setDefaultLookAndFeelDecorated(true)
        FlatAtomOneDarkContrastIJTheme.setup()
    }

    fun open() {
        init()
        RestructFrame().also {
            it.open()
            frame = it
        }
    }

    fun close() {
        frame.close()
    }
}