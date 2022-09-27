package dev.restruct.natives

import com.sun.jna.platform.WindowUtils
import com.sun.jna.platform.win32.Tlhelp32.*
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.IntByReference
import dev.restruct.natives.jna.IconExtract
import dev.restruct.natives.jna.KERNEL32
import dev.restruct.natives.jna.USER32

object ProcessList {

    private var cache: List<Process>? = null

    operator fun invoke(): List<Process> {
        if(cache != null) return cache!!
        val processes = mutableListOf<Process>()
        val entries = mutableListOf<PROCESSENTRY32>()

        val snapshot = KERNEL32.CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, WinDef.DWORD(0))
        var entry = PROCESSENTRY32()
        KERNEL32.Process32First(snapshot, entry)
        do {
            if(entry.th32ProcessID.toInt() != 0) {
                entries.add(entry)
            }
            entry = PROCESSENTRY32()
        } while(KERNEL32.Process32Next(snapshot, entry))
        entries.forEach { pe ->
            val mentries = mutableListOf<MODULEENTRY32W>()
            val msnapshot = KERNEL32.CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, pe.th32ProcessID)
            var mentry = MODULEENTRY32W()
            KERNEL32.Module32FirstW(msnapshot, mentry)
            do {
                mentries.add(mentry)
                mentry = MODULEENTRY32W()
            } while(KERNEL32.Module32NextW(snapshot, mentry))
            KERNEL32.CloseHandle(msnapshot)

            val proc = Process(pe)
            proc.path = mentries.firstOrNull()?.szExePath() ?: ""
            if(proc.path != "") {
                proc.icon = IconExtract.getIconForFile(16, 16, proc.path)
            }
            processes.add(proc)
        }

        KERNEL32.CloseHandle(snapshot)

        WindowUtils.getAllWindows(false).forEach { window ->
            val pid = IntByReference()
            USER32.GetWindowThreadProcessId(window.hwnd, pid)
            val proc = processes.firstOrNull { it.id == pid.value } ?: return@forEach
            proc.windows.add(window.hwnd)
        }

        cache = processes
        return processes
    }

}