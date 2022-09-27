package dev.restruct.natives

import com.sun.jna.Native
import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32
import com.sun.jna.platform.win32.WinDef.HWND
import java.awt.image.BufferedImage

class Process internal constructor(entry: PROCESSENTRY32) {
    val id = entry.th32ProcessID.toInt()
    val name = Native.toString(entry.szExeFile)
    val windows = mutableListOf<HWND>()
    var icon: BufferedImage? = null
    var path: String = ""
}