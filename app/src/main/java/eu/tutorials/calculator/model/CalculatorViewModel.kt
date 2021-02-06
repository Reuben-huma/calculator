package eu.tutorials.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private var _value = MutableLiveData<String>()
    val value: LiveData<String> get() = _value

    private lateinit var currentCharacter: String
    private var isStart = true
    private var isCurrentADigit: Boolean = false
    private var isPreviousADigit: Boolean = false
    private var nonDigitCount: Int = 0
    private var commaCount: Int = 0

    fun calculate(_character: String) {
        currentCharacter = _character

        when (currentCharacter) {
            "DEL" -> reset()
            "/", "x", "-", "+", "=", "," -> appendCharacter(false)
            else -> appendCharacter(true)
        }
    }

    private fun appendCharacter(isDigit: Boolean) {
        isCurrentADigit = isDigit

        if(isCurrentADigit) {
            isStart = false
            _value.value = "${value.value ?: ""}$currentCharacter"
            isPreviousADigit = true
        }
        else {
            if(isPreviousADigit) {
                if (currentCharacter == ",") {
                    if (commaCount == 0) {
                        _value.value = "${value.value ?: ""}$currentCharacter"
                        commaCount++
                    }
                } else if (nonDigitCount == 0) {
                    _value.value = "${value.value ?: ""}$currentCharacter"
                    nonDigitCount++
                    commaCount = 0
                }

                isPreviousADigit = false
            }
        }
    }

    private fun performArithmetic(operation: String) {}

    private fun reset() {
        isPreviousADigit = false
        _value.value = null
        isCurrentADigit = true
        commaCount = 0
        nonDigitCount = 0

    }
}