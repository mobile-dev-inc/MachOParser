package dev.mobile.machoparser

import java.io.File

fun main(args: Array<String>) {
    val file = File(args[0])
    if (!file.exists()) {
        println("No such file: $file")
    }

    println("CPU types of $file")

    val inputStream = file.inputStream()

    val cpuTypes = MachOParser.parseCPUTypes(inputStream)
    cpuTypes.forEach {
        println("\t$it")
    }
}
