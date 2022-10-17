package dev.mobile.machoparser

// From mach-o/loader.h

//struct mach_header {
//    unsigned long magic; /* Mach magic number identifier */
//    cpu_type_t cputype; /* cpu specifier */
//    cpu_subtype_t cpusubtype; /* machine specifier */
//    unsigned long filetype; /* type of file */
//    unsigned long ncmds; /* number of load commands */
//    unsigned long sizeofcmds; /* size of all load commands */
//    unsigned long flags; /* flags */
//};

data class MachOHeader(
    val magic: UInt,
    val cpuType: CPUType,
    val cpuSubtype: UInt,
    val filetype: UInt,
    val ncmds: UInt,
    val sizeofcmds: UInt,
    val flags: UInt
)


