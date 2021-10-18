package com.example.android.calculator


enum class Operators(val sign: Char, val precedence: Int) {
    PLUS('+', 2),
    MINUS('-', 2),
    MULTIPLY(Character.toChars("D7".toInt(radix = 16))[0], 3),
    DIVISION('/', 4);
}