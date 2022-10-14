import java.io.DataInputStream
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder.LITTLE_ENDIAN

object MachOParser {
    // From mach-o/fat.h
    const val FAT_MAGIC = 0xcafebabeU
    const val FAT_CIGAM = 0xbebafecaU

    // From mach-o/loader.h
    const val MH_MAGIC = 0xfeedfaceU
    const val MH_CIGAM = 0xcefaedfeU
    const val MH_MAGIC_64 = 0xfeedfacfU
    const val MH_CIGAM_64 = 0xcffaedfeU

    fun parseCPUTypes(content: InputStream): List<CPUType> {
        val stream = DataInputStream(content)
        val magic = readUInt(stream)

        return when(magic) {
            FAT_MAGIC -> parseFATHeader(stream).map { it.cputype }
            FAT_CIGAM -> parseFATHeader(stream).map { it.cputype }

            MH_MAGIC -> listOf(parseMachOHeader(magic, stream, true).cpuType)
            MH_CIGAM -> listOf(parseMachOHeader(magic, stream, false).cpuType)

            MH_MAGIC_64 -> listOf(parseMachOHeader(magic, stream, true).cpuType)
            MH_CIGAM_64 -> listOf(parseMachOHeader(magic, stream, false).cpuType)

            else -> throw MachOParserError("Magic header '0x${magic.toString(16)}' not recognised as FAT or Mach-O")
        }
    }

    private fun parseMachOHeader(magic: UInt, stream: DataInputStream, msb: Boolean): MachOHeader {
        return MachOHeader(
            magic = magic,
            cpuType = CPUType.from(readUInt(stream, msb)),
            cpuSubtype = readUInt(stream, msb),
            filetype = readUInt(stream, msb),
            ncmds = readUInt(stream, msb),
            sizeofcmds = readUInt(stream, msb),
            flags = readUInt(stream, msb),
        )
    }

    // FAT headers are always formatted in Big Endian, no need to swap byte order
    // for the different file types
    private fun parseFATHeader(stream: DataInputStream): List<FATArch> {
        val nfatArch = stream.readInt()
        val result = arrayListOf<FATArch>()
        repeat(nfatArch) {
            val fatArch = FATArch(
                cputype = CPUType.from(readUInt(stream)),
                cpusubtype = readUInt(stream),
                offset = readUInt(stream),
                size = readUInt(stream),
                align = readUInt(stream),
            )
            result += fatArch
        }
        return result
    }

    // Support UInt and LSB/MSB, inspired by DataInputStream.java
    @Throws(IOException::class)
    fun readUInt(stream: DataInputStream, msb: Boolean = true): UInt {
        val ch1: Int = stream.read()
        val ch2: Int = stream.read()
        val ch3: Int = stream.read()
        val ch4: Int = stream.read()
        if (ch1 or ch2 or ch3 or ch4 < 0) throw EOFException()
        return if (msb) {
            ((ch1 shl 24) + (ch2 shl 16) + (ch3 shl 8) + (ch4 shl 0)).toUInt()
        } else {
            ((ch4 shl 24) + (ch3 shl 16) + (ch2 shl 8) + (ch1 shl 0)).toUInt()
        }
    }
}
