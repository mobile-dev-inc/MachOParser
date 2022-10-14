
// From mach/machine.h

private val CPU_ARCH_ABI64 =         0x01000000U      /* 64 bit ABI */
private val CPU_ARCH_ABI64_32 =      0x02000000U      /* ABI for 64-bit hardware with 32-bit types; LP32 */

enum class CPUType(val encodedValue: UInt) {
    ANY((-1).toUInt()),
    VAX(1U),
    MC680x0(6U),
    X86(7U),
    X86_64(X86.encodedValue or CPU_ARCH_ABI64),
    MC98000(10U),
    HPPA(11U),
    ARM(12U),
    ARM64(ARM.encodedValue or CPU_ARCH_ABI64),
    ARM64_32(ARM.encodedValue or CPU_ARCH_ABI64_32),
    MC88000(13U),
    SPARC(14U),
    I860(15U),
    ALPHA(16U),
    POWERPC(18U),
    POWERPC64(POWERPC.encodedValue or CPU_ARCH_ABI64);

    companion object {
        fun from(value: UInt) = CPUType.values().first { it.encodedValue == value }
    }
}
