package com.example.android.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val defaultResult = ""

    private val result = MutableLiveData(defaultResult)
    fun getResult(): LiveData<String> = result

    /**
     * Calculate the expression given by `result`.
     */
    fun calculateResult() {
        result.value?.let {
            result.value = Parser.evaluateExpression(it).toInt().toString()
        }
    }

    /**
     * Insert `digit` as String at the end.
     */
    fun insertDigit(digit: Int) {
        result.value?.let {
            result.value = if (it != defaultResult) it.plus(digit) else digit.toString()
        }
    }

    /**
     * Remove digit at the end of `result`.
     */
    fun removeEndDigit() {
        result.value?.let {
            if (it.isNotEmpty()) result.value = it.dropLast(1)
        }
    }

    /**
     * Callback for div button.
     */
    fun insertDivOp() = insertOperator(Operators.DIVISION)

    /**
     * Callback for mul button.
     */
    fun insertMulOp() = insertOperator(Operators.MULTIPLY)

    /**
     * Callback for sub button.
     */
    fun insertMinusOp() = insertOperator(Operators.MINUS)

    /**
     * Callback for add button.
     */
    fun insertPlusOp() = insertOperator(Operators.PLUS)

    /**
     * Insert `operator` as String at the end.
     */
    private fun insertOperator(operator: Operators) {
        result.value?.let {
            if (it.isNotEmpty() && it.last() !in Operators.values().map { op -> op.sign }) {
                result.value = it.plus(operator.sign)
            }
        }
    }
}