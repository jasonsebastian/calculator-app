package com.example.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.android.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val calculatorViewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(calculatorViewModel) {
            getResult().observe(this@MainActivity) {
                binding.resultText.text = it
            }

            getIsDelete().observe(this@MainActivity) {
                toggleUiDeleteButton()
            }
        }

        populateDigits()
        populateSymbols()
        setOnClickListeners()
    }

    private fun toggleUiDeleteButton() {
        with(binding.deleteButton) {
            if (icon == null) {
                text = null
                setIconResource(R.drawable.ic_outline_backspace_24)
            } else {
                icon = null
                setText(R.string.clear_symbol)
            }
        }
    }

    private fun populateDigits() {
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
        }
    }

    private fun populateSymbols() {
        with(binding) {
            listOf(dotButton to ".", sumButton to "=", divButton to "/").forEach {
                it.first.text = it.second
            }
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            with(calculatorViewModel) {
                deleteButton.setOnClickListener { removeResult() }
                divButton.setOnClickListener { insertDivOp() }
                mulButton.setOnClickListener { insertMulOp() }
                subButton.setOnClickListener { insertMinusOp() }
                addButton.setOnClickListener { insertPlusOp() }
                sumButton.setOnClickListener { calculateResult() }
            }
        }
    }
}