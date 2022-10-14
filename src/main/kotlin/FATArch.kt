
// From mach-o/fat.h

//struct fat_arch
//{
//    cpu_type_t cputype;
//    cpu_subtype_t cpusubtype;
//    uint32_t offset;
//    uint32_t size;
//    uint32_t align;
//};

data class FATArch(
    val cputype: CPUType,
    val cpusubtype: UInt,
    val offset: UInt,
    val size: UInt,
    val align: UInt,
)
