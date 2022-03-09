package id.dipoengoro.bmicalculator

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import id.dipoengoro.bmicalculator.ext.getBMI
import id.dipoengoro.bmicalculator.ext.validateInput


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Declaring the inputs and the button
        val weightInput = findViewById<EditText>(R.id.editWeight)
        val heightInput = findViewById<EditText>(R.id.editHeight)
        val calculateButton = findViewById<Button>(R.id.buttonCalculate)
        val resultCard = findViewById<CardView>(R.id.cardResult)

        // Make the button functional
        calculateButton.setOnClickListener {
            calculateButton.isEnabled = false
            val weightString = weightInput.text.trim().toString()
            val heightString = heightInput.text.trim().toString()
            // Processing the input
            when (validateInput(heightString, weightString)) {
                1 -> buttonFunctioning(getString(R.string.all_blank), calculateButton)
                2 -> buttonFunctioning(getString(R.string.height_blank), calculateButton)
                3 -> buttonFunctioning(getString(R.string.weight_blank), calculateButton)
                else -> {
                    val weight = weightString.toFloat()
                    val height = heightString.toFloat()

                    resultCard.visibility = VISIBLE
                    displayResult(getBMI(height, weight), this@MainActivity)

                    // Make Keyboard hide
                    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as
                            InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
                    calculateButton.isEnabled = true
                }
            }
        }
    }

    private fun displayResult(bmi: Float, context: Context) {
        val indexText = findViewById<TextView>(R.id.textIndex)
        val resultText = findViewById<TextView>(R.id.textResult)
        val infoText = findViewById<TextView>(R.id.textInfo)

        indexText.text = bmi.toString()
        resultText.apply {
            when {
                bmi < 18.50 -> {
                    this.text = context.getString(R.string.result_under)
                    this.setTextColor(context.getColor(R.color.under_weight))
                }
                bmi in 18.50..24.99 -> {
                    this.text = context.getString(R.string.result_normal)
                    this.setTextColor(context.getColor(R.color.normal))
                }
                bmi in 25.00..29.99 -> {
                    this.text = context.getString(R.string.result_over)
                    this.setTextColor(context.getColor(R.color.over_weight))
                }
                bmi > 29.99 -> {
                    this.text = context.getString(R.string.result_obese)
                    this.setTextColor(context.getColor(R.color.obese))
                }
            }
        }
        infoText.text = getString(R.string.normal_index)
    }

    private fun buttonFunctioning(message: String, button: Button) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        button.isEnabled = true
    }
}