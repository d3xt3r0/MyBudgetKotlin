package com.example.mybudget

import android.R.attr.defaultValue
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val PREFERENCE_FILE_KEY = "thePrefKey"
    private val KEY_BALANCE = "prefBalanceKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var spentamountEmptyFlag = false

        val sharedPref = getSharedPreferences(
            PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

        val balance = sharedPref.getString(KEY_BALANCE, "Balance")
        balanceAmount.text = balance

        //Opening for the first time

        if (balanceAmount.text == "Balance"){
            balanceAmount.visibility = View.INVISIBLE
            spentAmount.hint = "Enter the budget"
            button.isEnabled = false
            button.setBackgroundColor(Color.LTGRAY)
        }
        else{
            spentAmount.hint = "Spent amount"
        }

        //When the amount is inserted , then only the button becomes active

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

        //When button clicked

        button.setOnClickListener {

            if (!spentAmount.text.isBlank()){
                if (balanceAmount.visibility == View.INVISIBLE){
                    balanceAmount.text = spentAmount.text
                    balanceAmount.visibility = View.VISIBLE
                    val editor = sharedPref.edit()
                    editor.putString(KEY_BALANCE, balanceAmount.text.toString());
                    editor.apply();
                }else{
                    balanceAmount.text = (Integer.parseInt(balanceAmount.text.toString()) -
                            Integer.parseInt(spentAmount.text.toString())).toString()
                    val editor = sharedPref.edit()
                    editor.putString(KEY_BALANCE, balanceAmount.text.toString());
                    editor.apply();
                }
            }else{
                Toast.makeText(applicationContext,"Enter a value first",Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                balanceAmount.text = "Balance"
                balanceAmount.visibility = View.INVISIBLE
                val sharedPref = getSharedPreferences(
                    PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
                spentAmount.hint = "Enter a budget"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
