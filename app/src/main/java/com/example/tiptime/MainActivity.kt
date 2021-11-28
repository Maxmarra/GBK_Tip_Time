package com.example.tiptime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
//First, get the text for the cost of service
//text attribute of an EditText is an Editable not String
        val stringInTextField: String = binding.costOfService.text.toString()

//Next, convert the text to a decimal number
        //сразу обрабатываем возможное пустое значение
        // ничего не ввели и нажали
        // тогда в cost придел либо дабл, либо null
        //но программа уже не крашнится
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            Toast.makeText(this, "Введите сумму!",
                Toast.LENGTH_LONG).show()

            return
        }

//Now you need the tip percentage,
// which the user selected from a RadioGroup of RadioButtons
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

//Calculate the tip and round it up
//Note the use of var instead of val.
// This is because you may need to round up the value
// if the user selected that option, so the value might change.
        var tip = tipPercentage * cost

//For a Switch element, you can check the isChecked attribute
// to see if the switch is "on".
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }


        displayTip(tip)


    }

    private fun displayTip(tip : Double) {
        //This gives you a number formatter you can use to format numbers as currency
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }


}
























