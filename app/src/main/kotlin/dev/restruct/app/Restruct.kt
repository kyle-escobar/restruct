package dev.restruct.app

import dev.restruct.app.ui.UI
import dev.restruct.app.util.inject
import org.koin.core.context.startKoin
import org.tinylog.kotlin.Logger
import kotlin.system.exitProcess

class Restruct {

    companion object {

        private val restruct: Restruct by inject()

        @JvmStatic
        fun main(args: Array<String>) {
            startKoin { modules(DI_MODULE) }
            restruct.start()
        }
    }

    private val ui: UI by inject()

    fun start() {
        Logger.info("Starting Restruct.")

        /*
         * Open the UI.
         */
        ui.open()
    }

    fun stop() {
        Logger.info("Stopping Restruct.")

        /*
         * Close the UI.
         */
        ui.close()

        exitProcess(0)
    }
}