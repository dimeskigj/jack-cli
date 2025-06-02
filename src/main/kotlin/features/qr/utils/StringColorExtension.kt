package features.qr.utils

fun String.toRgbaColorCode(): Int = when (length) {
    6 -> "FF$this".toLong(radix = 16)
    8 -> toLong(radix = 16)
    else -> error("Invalid color: $this. Accepts RGB & RGBA values, example: 000000 and FF000000 for White.")
}.toInt()