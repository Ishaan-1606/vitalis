package com.cprac.wearableproject

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UpdateDetails(navController: NavController, userViewModel: UserViewModel) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    var userName by remember { mutableStateOf(sharedPreferences.getString("userName", "User") ?: "User") }
    var userAge by remember { mutableStateOf(sharedPreferences.getString("userAge", "N/A") ?: "N/A") }
    var userHeight by remember { mutableStateOf(sharedPreferences.getString("userHeight", "N/A") ?: "N/A") }
    var userWeight by remember { mutableStateOf(sharedPreferences.getString("userWeight", "N/A") ?: "N/A") }
    LaunchedEffect(Unit) {
        userViewModel.name = userName
        userViewModel.age = userAge
        userViewModel.height = userHeight
        userViewModel.weight = userWeight
    }
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn() {
                item {
                    Text(
                        text = "Update User Details",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    OutlinedTextField(
                        value = userName,
                        onValueChange = {
                            userName = it
                            sharedPreferences.edit().putString("userName", it).apply()
                        },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
                item {
                    OutlinedTextField(
                        value = userAge,
                        onValueChange = {
                            userAge = it
                            sharedPreferences.edit().putString("userAge", it).apply()
                        },
                        label = { Text("Age") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
                item {
                    OutlinedTextField(
                        value = userHeight,
                        onValueChange = {
                            userHeight = it
                            sharedPreferences.edit().putString("userHeight", it).apply()
                        },
                        label = { Text("Height (cm)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
                item {
                    OutlinedTextField(
                        value = userWeight,
                        onValueChange = {
                            userWeight = it
                            sharedPreferences.edit().putString("userWeight", it).apply()
                        },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
                item {Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }}
            }
        }
    }
}
