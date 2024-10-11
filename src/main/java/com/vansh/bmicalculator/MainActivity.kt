package com.vansh.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vansh.bmicalculator.BmiApp
import com.vansh.bmicalculator.ui.theme.BMICalculatorTheme
import androidx.compose.material3.Surface


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                Surface {
                    BmiApp(applicationContext)
                }
            }
        }
    }
}

