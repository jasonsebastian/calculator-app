package com.jasonsb.calculator

import org.junit.Assert.*

import org.junit.Test
import kotlin.math.pow

class ParserTest {

    private val parser = Parser()
    private val delta = 0.1.pow(parser.precision)

    @Test
    fun evaluateExpression_addTwoIntegers_returnsHundred() {
        val expression = "73".plus(Operators.PLUS.sign).plus("27")
        assertEquals(parser.evaluateExpression(expression).toInt(), 100)
    }

    @Test
    fun evaluateExpression_addOneNegative_returnsMinFifty() {
        val expression = Operators.MINUS.sign.toString()
            .plus("73")
            .plus(Operators.PLUS.sign)
            .plus("23")
        assertEquals(parser.evaluateExpression(expression).toInt(), -50)
    }

    @Test
    fun evaluateExpression_subTwoIntegers_returnsFifty() {
        val expression = "73".plus(Operators.MINUS.sign).plus("23")
        assertEquals(parser.evaluateExpression(expression).toInt(), 50)
    }

    @Test
    fun evaluateExpression_subOneNegative_returnsMinHundred() {
        val expression = Operators.MINUS.sign.toString()
            .plus("73")
            .plus(Operators.MINUS.sign)
            .plus("27")
        assertEquals(parser.evaluateExpression(expression).toInt(), -100)
    }

    @Test
    fun evaluateExpression_mulTwoIntegers_returnsHundred() {
        var expression = "20".plus(Operators.MULTIPLY.sign).plus("5")
        assertEquals(parser.evaluateExpression(expression).toInt(), 100)
        expression = "0.5".plus(Operators.MULTIPLY.sign).plus("200")
        assertEquals(parser.evaluateExpression(expression).toInt(), 100)
    }

    @Test
    fun evaluateExpression_mulTwoNegatives_returnsHundred() {
        val expression = Operators.MINUS.sign.toString()
            .plus("20")
            .plus(Operators.MULTIPLY.sign)
            .plus(Operators.MINUS.sign)
            .plus("5")
        assertEquals(parser.evaluateExpression(expression).toInt(), 100)
    }

    @Test
    fun evaluateExpression_mulOneNegative_returnsMinHundred() {
        var expression = "20"
            .plus(Operators.MULTIPLY.sign)
            .plus(Operators.MINUS.sign)
            .plus("5")
        assertEquals(parser.evaluateExpression(expression).toInt(), -100)
        expression = Operators.MINUS.sign.toString()
            .plus("20")
            .plus(Operators.MULTIPLY.sign)
            .plus("5")
        assertEquals(parser.evaluateExpression(expression).toInt(), -100)
    }

    @Test
    fun evaluateExpression_divTwoIntegers_returnsHundredZeroOne() {
        var expression = "300".plus(Operators.DIVISION.sign).plus("3")
        assertEquals(parser.evaluateExpression(expression).toInt(), 100)
        expression = "1".plus(Operators.DIVISION.sign).plus("10")
        assertEquals(parser.evaluateExpression(expression).toDouble(), 0.1, delta)
    }

    @Test
    fun evaluateExpression_divTwoNegatives_returnsHundred() {
        val expression = Operators.MINUS.sign.toString()
            .plus("300")
            .plus(Operators.DIVISION.sign)
            .plus(Operators.MINUS.sign)
            .plus("3")
        assertEquals(parser.evaluateExpression(expression).toInt(), 100)
    }

    @Test
    fun evaluateExpression_divOneNegative_returnsMinHundred() {
        var expression = "300".plus(Operators.DIVISION.sign).plus(Operators.MINUS.sign).plus("3")
        assertEquals(parser.evaluateExpression(expression).toInt(), -100)
        expression = Operators.MINUS.sign.toString()
            .plus("300")
            .plus(Operators.DIVISION.sign)
            .plus("3")
        assertEquals(parser.evaluateExpression(expression).toInt(), -100)
    }

    @Test
    fun evaluateExpression_divZero_returnZero() {
        var expression = "0".plus(Operators.DIVISION.sign).plus("100")
        assertEquals(parser.evaluateExpression(expression).toInt(), 0)
        expression = "0".plus(Operators.DIVISION.sign).plus(Operators.MINUS.sign).plus("100")
        assertEquals(parser.evaluateExpression(expression).toInt(), 0)
    }

    @Test
    fun evaluateExpression_divByZero_throwDivideByZero() {
        val expression = "100".plus(Operators.DIVISION.sign).plus("0")
        assertThrows(DivideByZeroException::class.java) {
            parser.evaluateExpression(expression)
        }
    }

    @Test
    fun evaluateExpression_malformedExpression_throwBadSyntax() {
        var expression = "100".plus(Operators.DIVISION.sign)
        assertThrows(BadSyntaxException::class.java) {
            parser.evaluateExpression(expression)
        }
        expression = "100".plus(Operators.DIVISION.sign).plus(Operators.MINUS.sign)
        assertThrows(BadSyntaxException::class.java) {
            parser.evaluateExpression(expression)
        }
    }
}