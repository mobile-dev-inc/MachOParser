import CPUType.ARM64
import CPUType.X86_64
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MachOParserTests {
    @Test
    fun test_arm64() {
        val resourceURL = javaClass.getResource("DevToolTester_arm")!!
        val stream = resourceURL.openStream()
        val cpuTypes = MachOParser.parseCPUTypes(stream)
        assertEquals(listOf(ARM64), cpuTypes)
    }

    @Test
    fun test_x86_64() {
        val resourceURL = javaClass.getResource("DevToolTester_x86_64")!!
        val stream = resourceURL.openStream()
        val cpuTypes = MachOParser.parseCPUTypes(stream)
        assertEquals(listOf(X86_64), cpuTypes)
    }

    @Test
    fun test_fat_x86_64_arm64() {
        val resourceURL = javaClass.getResource("DevToolTester_fat_arm_x86_64")!!
        val stream = resourceURL.openStream()
        val cpuTypes = MachOParser.parseCPUTypes(stream)
        assertEquals(listOf(X86_64, ARM64), cpuTypes)
    }
}
