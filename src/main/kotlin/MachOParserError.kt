import java.lang.Exception

data class MachOParserError(override val message: String): Exception(message)
