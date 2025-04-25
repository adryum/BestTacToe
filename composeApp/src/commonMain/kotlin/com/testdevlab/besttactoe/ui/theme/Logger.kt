package com.testdevlab.besttactoe.ui.theme

import com.testdevlab.besttactoe.core.utils.isRelease
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun log(message: String) {
    val stackTrace = Thread.currentThread().stackTrace[2]
    val timeString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date().time)
    val log = "$timeString: (${stackTrace.fileName}:${stackTrace.lineNumber}) #${stackTrace.methodName}: $message"

    val out = java.io.PrintStream(System.out, true, "UTF-8")
    out.println(log)
}
