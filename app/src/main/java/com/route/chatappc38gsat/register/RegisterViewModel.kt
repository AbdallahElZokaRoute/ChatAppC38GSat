package com.route.chatappc38gsat.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.chatappc38gsat.base.BaseViewModel
import com.route.chatappc38gsat.model.AppUser
import com.route.chatappc38gsat.database.addUserToFirestoreDB
import com.route.chatappc38gsat.model.DataUtils

class RegisterViewModel : BaseViewModel<Navigator>() {

    val firstNameState = mutableStateOf("")
    val firstNameErrorState = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")
    val auth = Firebase.auth
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
        if (validateFields()) {
            // send To Firebase Auth
            isLoadingState.value = true
            auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
                .addOnCompleteListener { task ->
                    isLoadingState.value = false
                    if (task.isSuccessful) {
                        val userUID = task.result.user?.uid
                        sendDataToFirestore(userUID!!)

                    } else {
                        dialogMessageState.value =
                            task.exception?.localizedMessage ?: "Error Occurred"
                    }

                }
        }

    }

    fun sendDataToFirestore(uid: String) {
        isLoadingState.value = true
        val user = AppUser(uid, firstName = firstNameState.value, email = emailState.value)
        addUserToFirestoreDB(user, onSuccessListener = {
            isLoadingState.value = false
            // Navigate To Home Screen
            DataUtils.appUser = user
            navigator?.navigateToHomeScreen()
        }, onFailureListener = {
            isLoadingState.value = false
            dialogMessageState.value = it.localizedMessage ?: "Error Occurred"
        })
    }

}
