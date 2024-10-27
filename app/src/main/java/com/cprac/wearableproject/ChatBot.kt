package com.cprac.wearableproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ChatBot(
    viewModel: ChatBotVM = viewModel(),navController: NavController
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            ChatHeader()
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ChatList(list = viewModel.list)
            }
            ChatFooter { inputText ->
                if (inputText.isNotEmpty()) {
                    viewModel.sendMessage(inputText)
                }
            }
        }
    }

}