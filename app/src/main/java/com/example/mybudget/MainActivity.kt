package com.example.mybudget

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (balanceAmount.text == "Balance"){
            balanceAmount.visibility = View.INVISIBLE
            spentAmount.hint = "Enter the budget"
            button.isEnabled = false
            button.setBackgroundColor(Color.LTGRAY)
        }

        spentAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                button.isEnabled = true
                button.setBackgroundColor(Color.parseColor("#03DAC5"))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                button.isEnabled = false
                button.setBackgroundColor(Color.LTGRAY)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button.isEnabled = true
                button.setBackgroundColor(Color.parseColor("#03DAC5"))
            }


        })
    }
}
