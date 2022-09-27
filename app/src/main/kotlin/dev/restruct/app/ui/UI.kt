package dev.restruct.app.ui

class UI {

    lateinit var app: UIApp
    lateinit var window: Window

    fun open() {
        window = Window()
        window.open()
    }

    fun close() {
        app.stop()
        window.close()
    }
}