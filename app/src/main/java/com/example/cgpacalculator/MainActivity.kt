package com.example.cgpacalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.cgpacalculator.ui.theme.CGPACalculatorTheme
import com.example.cgpacalculator.ui.theme.MainColor

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CGPACalculatorTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false) // Edge-to-edge display
                window.navigationBarColor = MainColor.toArgb()
                val navScreenController= rememberNavController()
                Navigation(navScreenController)
            }
        }
    }
}