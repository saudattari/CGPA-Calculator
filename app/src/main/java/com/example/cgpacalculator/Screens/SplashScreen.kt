package com.example.cgpacalculator.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cgpacalculator.R
import com.example.cgpacalculator.ui.theme.MainColor
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    Scaffold(containerColor = MainColor) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(MainColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "CGPA Calculator",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 30.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Developed by\nSaud",
                    textAlign = TextAlign.Center ,
                    color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp))
            }

        LaunchedEffect(Unit) {
            delay(2000)
            navController.navigate("Main"){
                popUpTo("Splash"){
                    inclusive = true
                }
            }
        }
    }
    }
}