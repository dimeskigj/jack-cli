package utils

import features.qr.utils.toRgbaColorCode
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ColorUtilsTest {
    @Test
    fun `when valid hex string conversion should handle RGB and RGBA formats`() {
        assertEquals(0xFF00FF00.toInt(), "00FF00".toRgbaColorCode())
        assertEquals(0x8000FF00.toInt(), "8000FF00".toRgbaColorCode())
    }

    @Test
    fun `when invalid hex string conversion should throw exception`() {
        assertFailsWith<Exception> {
            "invalid".toRgbaColorCode()
        }

        assertFailsWith<Exception> {
            "12345".toRgbaColorCode()
        }
    }
}