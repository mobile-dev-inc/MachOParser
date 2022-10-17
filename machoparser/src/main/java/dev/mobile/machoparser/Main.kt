package dev.mobile.machoparser

import java.io.File

fun main(args: Array<String>) {
    val file = File("./machoparser/src/test/resources/DevToolTester_arm")
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
