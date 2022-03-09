package id.dipoengoro.bmicalculator.ext

import android.content.Context
import android.widget.Toast
import kotlin.math.pow
import kotlin.math.roundToInt

fun getBMI(height: Float, weight: Float): Float {
    val heightInMeter = height / 100.0
    val result = weight / heightInMeter.pow(2)
    return ((result * 100.0).roundToInt() / 100.0).toFloat()
}

fun validateInput(height: String?, weight: String?): Int {
    return when {
        height.isNullOrEmpty() && weight.isNullOrEmpty() -> 1
        height.isNullOrEmpty() -> 2
        weight.isNullOrEmpty() -> 3
        else -> 0
    }
}