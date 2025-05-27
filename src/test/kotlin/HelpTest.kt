import org.factotum.main
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class HelpTest {
    @Test
    fun `when '--help' argument passed expect no exception`() {
        assertDoesNotThrow {
            main(arrayOf("--help"))
        }
    }

    @Test
    fun `when '-h' argument passed expect no exception`() {
        assertDoesNotThrow {
            main(arrayOf("-h"))
        }
    }
}