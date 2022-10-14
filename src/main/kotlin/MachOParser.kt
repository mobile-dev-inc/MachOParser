import java.nio.ByteBuffer
import java.nio.ByteOrder.LITTLE_ENDIAN

class MachOParser {
    fun parseCPUTypes(content: ByteArray): List<CPUType> {
        val buffer = ByteBuffer.wrap(content)
        val magic = buffer.int.toUInt()

        return when(magic) {
            FAT_MAGIC -> parseFATHeader(buffer).map { it.cputype }
            // FAT headers are always formatted in Big Endian, no need to swap bytes
            FAT_CIGAM -> parseFATHeader(buffer).map { it.cputype }

            MH_MAGIC -> listOf(parseMachOHeader(magic, buffer).cpuType)
            MH_CIGAM -> listOf(parseMachOHeader(magic, buffer.order(LITTLE_ENDIAN)).cpuType)
            MH_MAGIC_64 -> listOf(parseMachOHeader(magic, buffer).cpuType)
            MH_CIGAM_64 -> listOf(parseMachOHeader(magic, buffer.order(LITTLE_ENDIAN)).cpuType)
            else -> throw MachOParserError("Magic header '0x${magic.toString(16)}' not recognised as FAT or MachO")
        }
    }

    private fun parseMachOHeader(magic: UInt, buffer: ByteBuffer): MachOHeader {
        return MachOHeader(
            magic = magic,
            cpuType = CPUType.from(buffer.int.toUInt()),
            cpuSubtype = buffer.int.toUInt(),
            filetype = buffer.int.toUInt(),
            ncmds = buffer.int.toUInt(),
            sizeofcmds = buffer.int.toUInt(),
            flags = buffer.int.toUInt(),
        )
    }

    private fun parseFATHeader(buffer: ByteBuffer): List<FATArch> {
        val nfatArch = buffer.int
        val result = arrayListOf<FATArch>()
        repeat(nfatArch) {
            val fatArch = FATArch(
                cputype = CPUType.from(buffer.int.toUInt()),
                cpusubtype = buffer.int.toUInt(),
                offset = buffer.int.toUInt(),
                size = buffer.int.toUInt(),
                align = buffer.int.toUInt(),
            )
            result += fatArch
        }
        return result
    }

    companion object {
        // From mach-o/fat.h
        const val FAT_MAGIC = 0xcafebabeU
        const val FAT_CIGAM = 0xbebafecaU

        // From mach-o/loader.h
        const val MH_MAGIC = 0xfeedfaceU
        const val MH_CIGAM = 0xcefaedfeU
        const val MH_MAGIC_64 = 0xfeedfacfU
        const val MH_CIGAM_64 = 0xcffaedfeU
    }
}
