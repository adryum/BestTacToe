package com.testdevlab.besttactoe.ui.theme

import com.testdevlab.besttactoe.core.isRelease
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun log(message: String) {
    val stackTrace = Thread.currentThread().stackTrace[2]
    val timeString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date().time)
    val log = "$timeString: (${stackTrace.fileName}:${stackTrace.lineNumber}) #${stackTrace.methodName}: $message"

    val out = java.io.PrintStream(System.out, true, "UTF-8")
    out.println(log)

    if (isRelease())
        writeLog(log)
}

val logFolder = File("C:\\Users\\Adrians\\Downloads\\BestTacToe\\BestTacToe\\composeApp\\src\\commonMain\\kotlin\\com\\testdevlab\\besttactoe\\core\\cache\\logs")
val logFile = File("releaseLogs.txt")
val backup = File("C:\\Users\\Adrians\\Downloads\\BestTacToe\\BestTacToe\\composeApp\\src\\commonMain\\kotlin\\com\\testdevlab\\besttactoe\\core\\cache\\logs\\backup")
var logLineCount = 0
val MAX_LINES = 99999999

fun writeLog(error: String) {
    write(
        folder = logFolder,
        file = logFile,
        backup = backup,
        lineCount = logLineCount,
        text = error
    ) {
        logLineCount = it
    }
}

private fun write(folder: File, file: File, backup: File, lineCount: Int, text: String, onLineCountUpdated: (Int) -> Unit) {
    var count = lineCount
    if (!folder.exists()) {
        folder.mkdir()
    }
    if (!file.exists()) {
        file.createNewFile()
    }
    if (count == -1) {
        count = 0
        onLineCountUpdated(count)
        file.forEachLine {
            onLineCountUpdated(++count)
        }
    }
    if (count >= MAX_LINES) {
        file.copyTo(backup)
        file.delete()
        file.createNewFile()
        onLineCountUpdated(-1)
        count = -1
    }

    file.appendText(text)
    file.appendText("\n")
    onLineCountUpdated(++count)
}