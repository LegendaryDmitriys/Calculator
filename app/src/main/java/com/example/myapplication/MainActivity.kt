package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var resultTextView: TextView
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var operation: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = LinearLayout(this)
        mainLayout.orientation = LinearLayout.VERTICAL
        mainLayout.gravity = Gravity.CENTER

        resultTextView = TextView(this)
        resultTextView.textSize = 30f
        resultTextView.gravity = Gravity.END
        val resultParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        resultParams.setMargins(10, 20, 10, 0) // Убираем отступ снизу
        resultTextView.layoutParams = resultParams
        resultTextView.setBackgroundResource(android.R.color.darker_gray)
        mainLayout.addView(resultTextView)

        val buttons = arrayOf(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        )

        for (i in buttons.indices step 4) {
            val buttonLayout = LinearLayout(this)
            buttonLayout.orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            buttonLayout.layoutParams = params

            for (j in i until i + 4) {
                if (j >= buttons.size) break
                val button = Button(this)
                button.text = buttons[j]
                button.setTextSize(24f)
                button.setTextColor(resources.getColor(android.R.color.white))
                val buttonParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                buttonParams.setMargins(5, 5, 5, 5)
                button.layoutParams = buttonParams
                button.setBackgroundResource(R.drawable.custom_button)
                button.setOnClickListener { view -> onButtonClick(view) }
                button.setOnTouchListener { v, event ->
                    if (event.action == android.view.MotionEvent.ACTION_DOWN) {
                        v.startAnimation(AlphaAnimation(1f, 0.7f))
                    } else if (event.action == android.view.MotionEvent.ACTION_UP) {
                        v.startAnimation(AlphaAnimation(0.7f, 1f))
                    }
                    false
                }
                buttonLayout.addView(button)
            }

            mainLayout.addView(buttonLayout)
        }

        setContentView(mainLayout)
    }

    private fun onButtonClick(view: View?) {
        val buttonText = (view as Button).text.toString()

        when {
            buttonText in "0123456789" -> appendDigit(buttonText)
            buttonText in "+-*/" -> setOperation(buttonText)
            buttonText == "=" -> calculateResult()
            buttonText == "C" -> clearAll()
        }
    }

    private fun setOperation(op: String) {
        operation = op
        operand1 = resultTextView.text.toString().toDouble()
        resultTextView.text = ""
    }

    private fun appendDigit(digit: String) {
        resultTextView.append(digit)
    }

    private fun clearAll() {
        resultTextView.text = ""
        operand1 = 0.0
        operand2 = 0.0
        operation = ""
    }

    private fun calculateResult() {
        operand2 = resultTextView.text.toString().toDouble()
        val result = when (operation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> 0.0
        }
        resultTextView.text = result.toString()
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}