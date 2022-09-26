package dev.restruct.natives

import com.sun.jna.platform.WindowUtils
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.IntByReference
import dev.restruct.natives.util.Kernel32
import dev.restruct.natives.util.User32

object ProcessList {

    fun load(): List<Process> {
        val processes = mutableListOf<Process>()
        val entries = mutableListOf<PROCESSENTRY32>()

        val snapshot = Kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, WinDef.DWORD(0))
        val entry = PROCESSENTRY32()
        Kernel32.Process32First(snapshot, entry)
        do {
            if(entry.th32ProcessID.toInt() != 0) {
                entries.add(entry)
            }
        } while(Kernel32.Process32Next(snapshot, entry))

        entries.forEach {
            processes.add(Process(it))
        }
        Kernel32.CloseHandle(snapshot)

        val windows = WindowUtils.getAllWindows(false)
        val pid = IntByReference()
        windows.forEach { window ->
            User32.GetWindowThreadProcessId(window.hwnd, pid)
            val proc = processes.firstOrNull { it.id == pid.value } ?: return@forEach
            proc.windows.add(window.hwnd)
        }

        return processes
    }

    operator fun get(pid: Int): Process? {
        return load().firstOrNull { it.id == pid }
    }

    operator fun get(processName: String): Process? {
        return load().firstOrNull { it.name == processName }
    }
}