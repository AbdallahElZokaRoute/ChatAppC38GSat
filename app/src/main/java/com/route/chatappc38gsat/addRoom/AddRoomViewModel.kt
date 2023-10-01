package com.route.chatappc38gsat.addRoom

import androidx.compose.runtime.mutableStateOf
import com.route.chatappc38gsat.base.BaseViewModel
import com.route.chatappc38gsat.database.addRoomToFirestoreDB
import com.route.chatappc38gsat.model.Category
import com.route.chatappc38gsat.model.Room

class AddRoomViewModel : BaseViewModel<Navigator>() {
    val roomNameState = mutableStateOf("")
    val roomNameErrorState = mutableStateOf("")

    val roomDescriptionState = mutableStateOf("")
    val roomDescriptionErrorState = mutableStateOf("")

    val isDropDownExpanded = mutableStateOf(false)

    // Data class
    val categories = Category.getCategoriesList()
    val selectedCategory = mutableStateOf(categories.get(0))
    fun validateFields(): Boolean {
        if (roomNameState.value.isEmpty() || roomNameState.value.isBlank()) {
            roomNameErrorState.value = "Room name Required"
            return false
        } else
            roomNameErrorState.value = ""
        if (roomDescriptionState.value.isEmpty() || roomDescriptionState.value.isBlank()) {
            roomDescriptionErrorState.value = "Room Description Required"
            return false
        } else
            roomDescriptionErrorState.value = ""

        return true
    }

    fun addRoomToFirestore() {
        if (validateFields()) {
            val room = Room(
                name = roomNameState.value,
                description = roomDescriptionState.value,
                categoryId = selectedCategory.value.categoryId
            )
            isLoadingState.value = true
            addRoomToFirestoreDB(room, onSuccessListener = {
                isLoadingState.value = false
                dialogMessageState.value = "Room Added Successfully"
            }, onFailureListener = {
                isLoadingState.value = false
                dialogMessageState.value = it.localizedMessage ?: "Error Occurred"
            })
        }
    }
}
