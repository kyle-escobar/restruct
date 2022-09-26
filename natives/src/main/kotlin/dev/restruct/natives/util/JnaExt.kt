package dev.restruct.natives.util

import com.sun.jna.platform.win32.GDI32

val Kernel32 get() = com.sun.jna.platform.win32.Kernel32.INSTANCE

val User32 get() = com.sun.jna.platform.win32.User32.INSTANCE

val Shell32 get() = com.sun.jna.platform.win32.Shell32.INSTANCE

val GDI32 get() = com.sun.jna.platform.win32.GDI32.INSTANCE