package com.route.chatappc38gsat.login

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

import com.route.chatappc38gsat.base.BaseViewModel
import com.route.chatappc38gsat.database.getUserFromFirestoreDB
import com.route.chatappc38gsat.model.AppUser
import com.route.chatappc38gsat.model.DataUtils

class LoginViewModel : BaseViewModel<Navigator>() {
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")
    val auth = Firebase.auth
    fun authenticate() {
        if (validateFields()) {
            isLoadingState.value = true
            auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
                .addOnCompleteListener { task ->
                    isLoadingState.value = false
                    if (task.isSuccessful) {
                        val userUID = task.result.user?.uid
                        isLoadingState.value = true
                        getUserFromFirestoreDB(userUID!!, onSuccessListener = {
                            isLoadingState.value = false
                            DataUtils.appUser = it.toObject(AppUser::class.java)
                            navigator?.navigateToHome()
                        }, onFailureListener = {
                            isLoadingState.value = false
                            dialogMessageState.value =
                                it.localizedMessage ?: "Invalid Email or password"
                        }
                        )
                    }

                }
        }
    }

    private fun validateFields(): Boolean {
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
}