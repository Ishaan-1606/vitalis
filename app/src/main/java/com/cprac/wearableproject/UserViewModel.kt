package com.cprac.wearableproject

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class UserViewModel : ViewModel() {
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var height by mutableStateOf("")
    var weight by mutableStateOf("")

    fun setUserDetails(name: String, age: String, height: String, weight: String) {
        this.name = name
        this.age = age
        this.height = height
        this.weight = weight
    }
}
//package com.cprac.wearableproject
//
//import androidx.lifecycle.ViewModel
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//
//class UserViewModel : ViewModel() {
//    var name by mutableStateOf("")
//    var dateOfBirth by mutableStateOf("")
//    var gender by mutableStateOf("")
//
//    fun setUserDetails(name: String, dateOfBirth: String, gender: String) {
//        this.name = name
//        this.dateOfBirth = dateOfBirth
//        this.gender = gender
//    }
//}