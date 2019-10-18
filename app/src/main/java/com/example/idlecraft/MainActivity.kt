package com.example.idlecraft

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberText.setText("" + number)

        buttonInc.setOnClickListener {
            numberText.setText("" + ++number)
        }

        buttonDec.setOnClickListener {
            numberText.setText("" + --number)
        }

        buttonRes.setOnClickListener {
            number = 0
            numberText.setText("" + number)
        }
    }

}
