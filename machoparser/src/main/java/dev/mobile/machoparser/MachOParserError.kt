package dev.mobile.machoparser

data class MachOParserError(override val message: String): Exception(message)
