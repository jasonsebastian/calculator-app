package com.example.android.calculator

class Parser {
    companion object {
        private val numStack = Stack<Double>()
        private val opStack = Stack<Char>()

        fun evaluateExpression(expression: String): Double {

            // Holds consecutive digits to form a number.
            val numString = StringBuilder()

            expression.forEach {
                if (it.isDigit()) {
                    numString.append(it)
                } else if (it isIn Operators.values()) {
                    numStack.push(numString.toNumber())
                    numString.clear()
                    if (opStack.isNotEmpty()) {
                        performForwardComputation(it)
                    }
                    opStack.push(it)
                }
            }

            // Convert remaining number and push to `numStack`.
            if (numString.isNotEmpty()) {
                numStack.push(numString.toNumber())
                numString.clear()
            }

            // Perform backward computation.
            while (!opStack.isEmpty()) {
                val op = opStack.pop()
                computeOnNumStack(op)
            }

            // The final result is on `numStack`.
            return numStack.pop()
        }

        private fun StringBuilder.toNumber() = this.toString().toDouble()

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
                computeOnNumStack(prevOp)
                if (opStack.isEmpty())
                    break
                prevOpPrecedence = getBinaryOperatorPrecedence(opStack.peek())
            }
        }

        private fun getBinaryOperatorPrecedence(operator: Char): Int {
            return Operators.values().first { it.sign == operator }.precedence
        }

        private fun computeOnNumStack(operator: Char) {
            val num0 = numStack.pop()
            val num1 = numStack.pop()
            numStack.push(
                when (operator) {
                    Operators.PLUS.sign -> num1 + num0
                    Operators.MINUS.sign -> num1 - num0
                    Operators.MULTIPLY.sign -> num1 * num0
                    Operators.DIVISION.sign -> num1 / num0
                    else -> (-1).toDouble()
                }
            )
        }

        private infix fun <T> Char.isIn(operators: Array<T>): Boolean {
            return operators.any { it is Operators && this == it.sign }
        }
    }
}

class Stack<T> {
    private val stack = arrayListOf<T>()

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
}