package com.route.chatappc38gsat.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class BaseViewModel<Navigator> : ViewModel() {
    val isLoadingState = mutableStateOf(false)
    val dialogMessageState = mutableStateOf("")
    var navigator: Navigator? = null

}
