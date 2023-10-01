package com.route.chatappc38gsat.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.chatappc38gsat.database.getUserFromFirestoreDB
import com.route.chatappc38gsat.model.AppUser
import com.route.chatappc38gsat.model.DataUtils

class SplashViewModel : ViewModel() {
    val auth = Firebase.auth
    var navigator: Navigator? = null
    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    fun navigate() {
        if (isUserAuthenticated()) {
            getUserFromFirestoreDB(auth.currentUser?.uid!!, onSuccessListener = {
                val user = it.toObject(AppUser::class.java)
                DataUtils.appUser = user
                DataUtils.firebaseUser = auth.currentUser
                navigateToHomeScreen()
            }, onFailureListener = {
                navigateToLoginScreen()
            })
        } else {
            navigateToLoginScreen()
        }
    }

    fun navigateToHomeScreen() {
        navigator?.navigateToHome()
    }

    fun navigateToLoginScreen() {
        navigator?.navigateToLogin()
    }

}
