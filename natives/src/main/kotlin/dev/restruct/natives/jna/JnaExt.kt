package dev.restruct.natives.jna

val KERNEL32 get() = com.sun.jna.platform.win32.Kernel32.INSTANCE

val USER32 get() = com.sun.jna.platform.win32.User32.INSTANCE

val SHELL32 get() = com.sun.jna.platform.win32.Shell32.INSTANCE