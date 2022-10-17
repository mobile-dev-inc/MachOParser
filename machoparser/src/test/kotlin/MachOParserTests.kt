import com.google.common.truth.Truth.assertThat
import dev.mobile.machoparser.CPUType.ARM64
import dev.mobile.machoparser.CPUType.X86_64
import dev.mobile.machoparser.MachOParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
        assertThat(cpuTypes).containsExactly(X86_64, ARM64)
    }
}
