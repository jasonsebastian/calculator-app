package com.example.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.android.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calculatorViewModel: CalculatorViewModel by viewModels()
        calculatorViewModel.getResult().observe(this) {
            binding.resultText.text = it.toString()
        }

        with(binding) {
            resultText.text = calculatorViewModel.getResult().value.toString()
            val clickListener = KeypadAdapter.DigitClickListener {
                calculatorViewModel.updateResult(it)
            }
            keypadList.adapter = KeypadAdapter(clickListener)
        }
    }
}