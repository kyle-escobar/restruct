package dev.restruct.natives

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32
import com.sun.jna.platform.win32.WinDef.DWORDByReference
import com.sun.jna.platform.win32.WinDef.HICON
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.LPARAM
import com.sun.jna.platform.win32.WinDef.WPARAM
import com.sun.jna.platform.win32.WinGDI.*
import com.sun.jna.platform.win32.WinNT.PROCESS_ALL_ACCESS
import com.sun.jna.platform.win32.WinUser.*
import dev.restruct.natives.util.GDI32
import dev.restruct.natives.util.Kernel32
import dev.restruct.natives.util.Shell32
import dev.restruct.natives.util.User32
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import kotlin.experimental.and

class Process(entry: PROCESSENTRY32) {

    val id = entry.th32ProcessID.toInt()
    val parentId = entry.th32ParentProcessID.toInt()
    val name = Native.toString(entry.szExeFile)
    val handle = Kernel32.OpenProcess(PROCESS_ALL_ACCESS, false, id)

    val modules = mutableListOf<Any>()
    val windows = mutableListOf<HWND>()

    private var iconCache: ImageIcon? = null
    val icon: ImageIcon get() {
        if(iconCache != null) return iconCache!!
        var hicon: HICON? = extractSmallIcon(name, 1)?.let { HICON(it) }
        if(hicon == null) {
            hicon = User32.GetAncestor(windows[0], GA_ROOTOWNER).let { getHIcon(it) }
        }
        return if(hicon != null) ImageIcon(getIcon(hicon))
        else ImageIcon()
    }

    private fun extractSmallIcon(file: String, nIcons: Int): Pointer? {
        val hicons = arrayOfNulls<HICON>(1)
        Shell32.ExtractIconEx(file, 0, null, hicons, nIcons)
        return hicons[0]?.pointer
    }

    private fun sendMessageTimeout(hwnd: HWND, msg: Int, wparam: WPARAM, lparam: LPARAM, flags: Int, timeout: Int): Pointer {
        val result = DWORDByReference()
        val ret = User32.SendMessageTimeout(hwnd, msg, wparam, lparam, flags, timeout, result)
        if(ret.toInt() == 0) throw RuntimeException()
        return Pointer.createConstant(result.value.toLong())
    }

    private fun getHIcon(hwnd: HWND): HICON? {
        try {
            val icon = sendMessageTimeout(hwnd, WM_GETICON, WPARAM(ICON_SMALL.toLong()), LPARAM(0L), SMTO_NORMAL, 20)
            if(Pointer.nativeValue(icon) != 0L) {
                return User32.CopyIcon(HICON(icon))
            }
        } catch (_: Exception) {}

        try {
            val icon = sendMessageTimeout(hwnd, WM_GETICON, WPARAM(ICON_BIG.toLong()), LPARAM(0L), SMTO_NORMAL, 20)
            if(Pointer.nativeValue(icon) != 0L) {
                return User32.CopyIcon(HICON(icon))
            }
        } catch (_: Exception) {}

        try {
            val icon = sendMessageTimeout(hwnd, WM_GETICON, WPARAM(ICON_SMALL2.toLong()), LPARAM(0L), SMTO_NORMAL, 20)
            if(Pointer.nativeValue(icon) != 0L) {
                return User32.CopyIcon(HICON(icon))
            }
        } catch (_: Exception) {}

        try {
            val icon = User32.GetClassLong(hwnd, GCL_HICONSM)
            if(icon != 0) {
                return User32.CopyIcon(HICON(Pointer.createConstant(icon)))
            }
        } catch (_: Exception) {}

        try {
            val icon = User32.GetClassLong(hwnd, GCL_HICON)
            if(icon != 0) {
                return User32.CopyIcon(HICON(Pointer.createConstant(icon)))
            }
        } catch (_: Exception) {}

        return null
    }

    private fun getIcon(hicon: HICON): BufferedImage {
        val width = 16
        val height = 16
        val depth = 24.toShort()
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        val bitsColor = Memory((width * height * depth / 8).toLong())
        val bitsMask = Memory((width * height * depth / 8).toLong())
        val info = BITMAPINFO()
        val hdr = BITMAPINFOHEADER()
        info.bmiHeader = hdr
        hdr.biWidth = width
        hdr.biHeight = height
        hdr.biPlanes = 1
        hdr.biBitCount = depth
        hdr.biCompression = BI_RGB

        val hdc = User32.GetDC(null)
        val iconInfo = ICONINFO()
        User32.GetIconInfo(hicon, iconInfo)

        GDI32.GetDIBits(hdc, iconInfo.hbmColor, 0, height, bitsColor, info, DIB_RGB_COLORS)
        GDI32.GetDIBits(hdc, iconInfo.hbmMask, 0, height, bitsMask, info, DIB_RGB_COLORS)

        var r = 0
        var g = 0
        var b = 0
        var a = 0
        var argb = 0
        var x = 0
        var y = height - 1

        var i = 0
        while(i < bitsColor.size()) {
            b = (bitsColor.getByte(i.toLong()) and 0xFF.toByte()).toInt()
            g = (bitsColor.getByte((i + 1).toLong()) and 0xFF.toByte()).toInt()
            r = (bitsColor.getByte((i + 2).toLong()) and 0xFF.toByte()).toInt()
            a = 0xFF - bitsColor.getByte(i.toLong()) and 0xFF


            argb = (a shl 24) or (r shl 16) or (g shl 8) or b
            image.setRGB(x, y, argb)
            x = (x + 1) % width
            if(x == 0) {
                y--
            }
            i += 3
        }

        User32.ReleaseDC(null, hdc)
        GDI32.DeleteObject(iconInfo.hbmColor)
        GDI32.DeleteObject(iconInfo.hbmMask)

        return image
    }
}