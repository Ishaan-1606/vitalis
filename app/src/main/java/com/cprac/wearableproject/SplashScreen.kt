package com.cprac.wearableproject

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    val animatedScale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedScale.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        // Do not navigate to the login page until the button is pressed
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.splash_background), // Your background image
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xAA000000)) // Translucent black background for the text
                    .padding(16.dp) // Padding around the text
                    .clip(RoundedCornerShape(36.dp))
            ) {
                Text(
                    text = "Healthify-U",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp)) // Space between texts
            Box(
                modifier = Modifier
                    .background(Color(0xAA000000)) // Translucent black background for the text
                    .padding(16.dp) // Padding around the text
            ) {
                Text(
                    text = "Tech Meets Innovation",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Get Started Button
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp) // Position the button 20% above the bottom
                .fillMaxWidth(0.8f) // Optional: Adjust button width

        ) {
            Text("Get Started")
        }
    }
}

