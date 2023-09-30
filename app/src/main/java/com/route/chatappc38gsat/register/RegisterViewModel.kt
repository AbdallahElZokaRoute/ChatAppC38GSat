package com.route.chatappc38gsat.register

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    val firstNameState = mutableStateOf("")
    val firstNameErrorState = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")

    fun validateFields(): Boolean {
        if (firstNameState.value.isEmpty() || firstNameState.value.isBlank()) {
            firstNameErrorState.value = "First name Required"
            return false
        } else
            firstNameErrorState.value = ""
        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailErrorState.value = "Email Required"
            return false
        } else
            emailErrorState.value = ""
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()) {
            passwordErrorState.value = "Password Required"
            return false
        } else
            passwordErrorState.value = ""
        return true
    }

    fun sendDataToFirebaseAuth() {

        
    }

}
