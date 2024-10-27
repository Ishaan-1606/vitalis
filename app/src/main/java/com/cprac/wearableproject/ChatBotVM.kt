package com.cprac.wearableproject

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

@SuppressLint("SecretInSource")
class ChatBotVM:ViewModel() {
    val list = mutableStateListOf<ChatData>()
    //coroutines that's why by lazy
    private val genAi by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyA5k1ahvGQADhh_t9Wl5xataridtxcVKgw"
        )
    }
    fun sendMessage(message: String)=viewModelScope.launch {
        val chat:Chat=genAi.startChat()
        list.add(ChatData(message,ChatRoleEnum.USER.role))
        chat.sendMessage(
            content(ChatRoleEnum.USER.role){text(message)}
        ).text?.let{
            list.add(ChatData(it,ChatRoleEnum.MODEL.role))
        }
        val response=genAi.startChat().sendMessage(prompt=message).text
        Log.d("AI_ANS",response.toString())
    }
}
