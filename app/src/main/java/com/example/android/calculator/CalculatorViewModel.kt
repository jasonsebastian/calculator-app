package com.example.android.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val defaultResult = 0

    private val result = MutableLiveData(defaultResult)
    fun getResult(): LiveData<Int> = result

    fun updateResult(digit: Int) {
        result.value = digit
    }
}