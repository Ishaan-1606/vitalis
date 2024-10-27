package com.cprac.wearableproject

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun UserForm(navController: NavController, userViewModel: UserViewModel) {
    var name by remember { mutableStateOf(userViewModel.name) }
    var age by remember { mutableStateOf(userViewModel.age) }
    var height by remember { mutableStateOf(userViewModel.height) }
    var weight by remember { mutableStateOf(userViewModel.weight) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Step 2 of 3",
                style = MaterialTheme.typography.labelLarge
            )
        }

        item {
            Text(
                text = "Tell us about yourself",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = "Personal Details",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            Text(
                text = "We use this information to provide personalized recommendations.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        item {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    userViewModel.name = it
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = age,
                onValueChange = {
                    age = it
                    userViewModel.age = it
                },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = height,
                onValueChange = {
                    height = it
                    userViewModel.height = it
                },
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    userViewModel.weight = it
                },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (errorMessage.isNotEmpty()) {
            item {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        item {
            Button(
                onClick = {
                    if (validateInputs()) {
                        userViewModel.setUserDetails(name, age, height, weight)
                        val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putBoolean("formFilled", true)
                            putString("userName", name)
                            putString("userAge", age)
                            putString("userHeight", height)
                            putString("userWeight", weight)
                            apply()
                        }
                        navController.navigate("dashboard")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next")
            }
        }
    }
}
