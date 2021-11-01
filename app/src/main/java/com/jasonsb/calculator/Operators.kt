package com.jasonsb.calculator


enum class Operators(val sign: Char, val precedence: Int) {
    PLUS("2B".toUnicode(), 2),
    MINUS("2212".toUnicode(), 2),
    MULTIPLY("D7".toUnicode(), 3),
    DIVISION("F7".toUnicode(), 4);
}

private fun String.toUnicode() = Character.toChars(toInt(radix = 16))[0]