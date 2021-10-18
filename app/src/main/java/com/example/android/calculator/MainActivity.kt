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
            binding.resultText.text = it
        }

        with(binding) {
            listOf(
                zeroButton,
                oneButton,
                twoButton,
                threeButton,
                fourButton,
                fiveButton,
                sixButton,
                sevenButton,
                eightButton,
                nineButton
            ).forEachIndexed { index, element ->
                element.apply {
                    text = index.toString()
                    setOnClickListener { calculatorViewModel.insertDigit(index) }
                }
            }
            listOf(dotButton to ".", sumButton to "=", divButton to "/").forEach {
                it.first.text = it.second
            }
            with(calculatorViewModel) {
                resultText.text = getResult().value
                deleteButton.setOnClickListener { removeEndDigit() }
                divButton.setOnClickListener { insertDivOp() }
                mulButton.setOnClickListener { insertMulOp() }
                subButton.setOnClickListener { insertMinusOp() }
                addButton.setOnClickListener { insertPlusOp() }
                sumButton.setOnClickListener { calculateResult() }
            }
        }
    }
}