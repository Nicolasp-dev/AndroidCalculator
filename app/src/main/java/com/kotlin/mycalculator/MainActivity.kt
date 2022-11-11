package com.kotlin.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  private var tvInput: TextView? = null
  var lastNumeric: Boolean = false
  var lastDot: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    tvInput = findViewById(R.id.tvInput)
  }

  // 1. Get and show the user input data (numbers) on the screen => tvInput
  // 2. Set two boolean flags values, if its a number type lastNumeric<T> lastDot<F>
  fun onDigit(view: View){
    tvInput?.append((view as Button).text)
    lastNumeric = true
    lastDot = false
  }

  // Clear the text that is show to the user
  fun onClear(view:View){
    tvInput?.text = ""
  }

  // 1. Check if the last value of the input data is a number.
  // 2. If the last data is a number, allows the user to use the "dot" button.
  // 3. It also set the Boolean flags = lastNumeric<F> lastDot<T>
  fun onDecimalPoint(view: View){
      if(lastNumeric && !lastDot){
        tvInput?.append(".")
        lastNumeric = false
        lastDot = true
      }
  }

  // 1. If tvInput and text are non null:
  // 1.1. If the last input data is a number && it doesn't have an operation
  // 1.2. Append the View text content (Operator) to the output text
  // 1.3. Set both boolean flags to false.
  fun onOperator(view: View){
    tvInput?.text?.let{
      if(lastNumeric && !isOperatorAdded(it.toString())){
        tvInput?.append((view as Button).text)
        lastNumeric = false
        lastDot = false
      }
    }
  }


  fun onEqual(view: View) {
    if(lastNumeric){
      // Take the user input as a string and safe it into a variable
      var tvValue = tvInput?.text.toString()
      var prefix = ""

      try {
        // If it's a negative number
        if (tvValue.startsWith("-")){
          // Store the minus sign in the prefix var
          prefix = "-"
          // Set the textValue to the string starting at index 1. (Ignore the minus sign)
          tvValue = tvValue.substring(1)
        }
        // If the input data contains a minus sign
        if(tvValue.contains("-")) {
          // Split it by the minus sign and safe the data into an Array.
          val splitValue = tvValue.split("-")
          // Assign both split values to individual a var.
          var one = splitValue[0]
          var two = splitValue[1]
          // If prefix has the minus sign, concat "-" to the first split value
          if (prefix.isNotEmpty()) {
            one = prefix + one
          }
          // Operation logic and display the result on the output text as a string.
          tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
        } else if(tvValue.contains("+")) {
          val splitValue = tvValue.split("+")
          var one = splitValue[0]
          var two = splitValue[1]
          if (prefix.isNotEmpty()) {
            one = prefix + one
          }
          tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
        } else if(tvValue.contains("*")) {
          val splitValue = tvValue.split("*")
          var one = splitValue[0]
          var two = splitValue[1]
          if (prefix.isNotEmpty()) {
            one = prefix + one
          }
          tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
        } else if(tvValue.contains("/")) {
          val splitValue = tvValue.split("/")
          var one = splitValue[0]
          var two = splitValue[1]
          if (prefix.isNotEmpty()) {
            one = prefix + one
          }
          tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
        }

      }catch(e: ArithmeticException){
        e.printStackTrace()
      }
    }
  }

  private fun removeZeroAfterDot(result: String): String{
    var value = result
    if(result.contains(".0"))
      value = result.substring(0, result.length -2)
    return value
  }

  private fun isOperatorAdded(value: String): Boolean{
    return if(value.startsWith("-")){
      false
    } else {
      value.contains("/") ||
      value.contains("*") ||
      value.contains("+") ||
      value.contains("-")

    }
  }
}