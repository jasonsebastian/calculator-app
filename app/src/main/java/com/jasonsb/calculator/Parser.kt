package com.jasonsb.calculator

class Parser {
    private val numStack = Stack<Double>()
    private val opStack = Stack<Char>()
    val precision = 9

    fun evaluateExpression(expression: String): Number {
        val numString = StringBuilder() // Holds consecutive digits to form a number.

        for ((i, char) in expression.withIndex()) {
            if (char == Operators.MINUS.sign && (i == 0 || opStack.isNotEmpty())) {
                numString.append('-')
            } else if (char.isDigit() || char == '.') {
                numString.append(char)
            } else if (char isIn Operators.values()) {
                numString.moveNumberToNumStack()
                if (opStack.isNotEmpty()) {
                    performForwardComputation(char)
                }
                opStack.push(char)
            }
        }

        if (numString.isNotEmpty()) {
            numString.moveNumberToNumStack()
        }
        performBackwardComputation()

        return numStack.getFinalResult()
    }

    private infix fun <T> Char.isIn(operators: Array<T>): Boolean {
        return operators.any { it is Operators && this == it.sign }
    }

    private fun StringBuilder.moveNumberToNumStack() {
        numStack.push(toNumber()).also { clear() }
    }

    private fun StringBuilder.toNumber(): Double {
        if (toString() == "-") {
            numStack.clear()
            opStack.clear()
            throw BadSyntaxException()
        }
        return toString().toDouble()
    }

    /**
     * Since we are using stack, by default computation is done back-to-front. Hence for any
     * forward computation, we need to make sure that they are completed beforehand.
     *
     * Examples:
     * 4 * 3 + 2 -> 4 * 3 needs to be calculated first and 12 is stored in `numStack`.
     * 4 - 3 - 2 -> 4 - 3 needs to be calculated first and 1 is stored in `numStack`.
     */
    private fun performForwardComputation(it: Char) {
        var prevOpPrecedence = getBinaryOperatorPrecedence(opStack.peek())
        val currOpPrecedence = getBinaryOperatorPrecedence(it)
        while (prevOpPrecedence >= currOpPrecedence) {
            val prevOp = opStack.pop()
            numStack.computeWith(prevOp)
            if (opStack.isEmpty())
                break
            prevOpPrecedence = getBinaryOperatorPrecedence(opStack.peek())
        }
    }

    private fun getBinaryOperatorPrecedence(operator: Char): Int {
        return Operators.values().first { it.sign == operator }.precedence
    }

    private fun Stack<Double>.computeWith(operator: Char) {
        checkBadSyntax()
        val num0 = pop()
        val num1 = pop()
        checkDivideByZero(num0, operator)
        push(
            when (operator) {
                Operators.PLUS.sign -> num1.plus(num0)
                Operators.MINUS.sign -> num1.minus(num0)
                Operators.MULTIPLY.sign -> num1.times(num0)
                Operators.DIVISION.sign -> num1.div(num0)
                else -> (-1).toDouble()
            }
        )
    }

    private fun checkBadSyntax() {
        if (numStack.size < 2) {
            numStack.clear()
            opStack.clear()
            throw BadSyntaxException()
        }
    }

    private fun checkDivideByZero(num1: Double, operator: Char) {
        if (num1 == 0.0 && operator == Operators.DIVISION.sign) {
            numStack.clear()
            opStack.clear()
            throw DivideByZeroException()
        }
    }

    private fun performBackwardComputation() {
        while (!opStack.isEmpty()) {
            val op = opStack.pop()
            numStack.computeWith(op)
        }
    }

    private fun Stack<Double>.getFinalResult(): Number {
        val result = pop()
        return if (result.toInt().toDouble() == result) result.toInt() else result.round(9)
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}

class Stack<T> {
    private val stack = arrayListOf<T>()

    val size: Int
        get() = stack.size

    // Holds the top most
    private var top = -1

    fun push(item: T) {
        stack.add(item)
        top++
    }

    fun pop(): T = stack.removeAt(top--)

    fun peek(): T = stack[top]

    fun isEmpty() = stack.isEmpty()

    fun isNotEmpty() = stack.isNotEmpty()

    fun clear() {
        stack.clear()
        top = -1
    }
}

class DivideByZeroException(msg: String = "Can't divide by zero") : Exception(msg)

class BadSyntaxException(msg: String = "Bad syntax") : Exception(msg)