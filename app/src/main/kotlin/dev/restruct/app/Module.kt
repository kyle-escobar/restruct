package dev.restruct.app

import dev.restruct.app.ui.UI
import org.koin.dsl.module

val DI_MODULE = module {
    single { Restruct() }
    single { UI() }
}