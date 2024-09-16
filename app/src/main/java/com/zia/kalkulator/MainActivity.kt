package com.zia.kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var expressionTextView: TextView
    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonEquals: Button
    private lateinit var buttonDot: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonDelete: Button

    private var operand: Double = 0.0
    private var operation: String = ""
    private var expression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.show()
        supportActionBar?.title = "Kalkulator"

        resultTextView = findViewById(R.id.resultTextView)
        expressionTextView = findViewById(R.id.expressionTextView)

        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonEquals = findViewById(R.id.buttonEquals)
        buttonDot = findViewById(R.id.buttonDot)
        buttonClear = findViewById(R.id.buttonClear)
        buttonDelete = findViewById(R.id.buttonDelete)

        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        numberButtons.forEach { button ->
            button.setOnClickListener {
                val currentText = resultTextView.text.toString()
                val number = button.text.toString()
                resultTextView.text = if (currentText == "0") number else currentText + number
                expression += number
                expressionTextView.text = expression
            }
        }

        buttonDot.setOnClickListener {
            val currentText = resultTextView.text.toString()
            if (!currentText.contains(",")) {
                resultTextView.text = currentText + ","
                expression += ","
                expressionTextView.text = expression
            }
        }

        buttonPlus.setOnClickListener { setOperation("+") }
        buttonMinus.setOnClickListener { setOperation("-") }
        buttonMultiply.setOnClickListener { setOperation("x") }
        buttonDivide.setOnClickListener { setOperation("÷") }

        buttonEquals.setOnClickListener { calculateResult() }
        buttonClear.setOnClickListener { clearCalculator() }
        buttonDelete.setOnClickListener { deleteLastCharacter() }

        buttonDelete.setOnClickListener {
            val currentText = resultTextView.text.toString()

            if (currentText.isNotEmpty() && currentText != "0") {
                val newText = currentText.substring(0, currentText.length - 1)
                resultTextView.text = if (newText.isEmpty()) "0" else newText
            }

            if (expression.isNotEmpty()) {
                expression = expression.substring(0, expression.length - 1)
                expressionTextView.text = expression
            }
        }

    }
    private fun setOperation(op: String) {
        operand = resultTextView.text.toString().toDouble()
        operation = op
        expression += " $op "
        resultTextView.text = "0"
        expressionTextView.text = expression
    }

    private fun calculateResult() {
        val secondOperand = resultTextView.text.toString().toDouble()
        val result = when (operation) {
            "+" -> operand + secondOperand
            "-" -> operand - secondOperand
            "x" -> operand * secondOperand
            "÷" -> operand / secondOperand
            else -> 0.0
        }
        val formattedResult = if (result == result.toInt().toDouble()) {
            result.toInt().toString()  // Convert to Int if it's a whole number to remove ".0"
        } else {
            result.toString()  // Otherwise, keep it as a Double
        }

        // Update the expression and result views
        expression += " = $formattedResult"
        resultTextView.text = formattedResult
        expressionTextView.text = expression

        // Update the expression variable with the result for future operations
        expression = formattedResult
    }

    private fun clearCalculator() {
        resultTextView.text = "0"
        expressionTextView.text = ""
        operand = 0.0
        operation = ""
        expression = ""
    }

    private fun deleteLastCharacter() {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty() && currentText != "0") {
            resultTextView.text = currentText.dropLast(1)
            if (resultTextView.text.isEmpty()) {
                resultTextView.text = "0"
            }
        }
    }
}