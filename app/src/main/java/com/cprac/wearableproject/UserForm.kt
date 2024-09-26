package com.cprac.wearableproject

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun UserForm(navController: NavController, userViewModel: UserViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    fun validateInputs(): Boolean {
        return when {
            name.isBlank() -> {
                errorMessage = "Please enter your name."
                false
            }
            age.isBlank() || age.toIntOrNull() == null || age.toInt() !in 1..120 -> {
                errorMessage = "Please enter a valid age between 1 and 120."
                false
            }
            height.isBlank() || height.toFloatOrNull() == null || height.toFloat() !in 50f..250f -> {
                errorMessage = "Please enter a valid height between 50 and 250 cm."
                false
            }
            weight.isBlank() || weight.toFloatOrNull() == null || weight.toFloat() !in 20f..500f -> {
                errorMessage = "Please enter a valid weight between 20 and 500 kg."
                false
            }
            else -> {
                errorMessage = ""
                true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Step 2 of 3",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = "Tell us about yourself",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Personal Details",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "We use this information to provide personalized recommendations.",
            style = MaterialTheme.typography.bodySmall
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (validateInputs()) {
                    userViewModel.setUserDetails(name, age, height, weight)
                    navController.navigate("dashboard")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text("Next")
        }
    }
}