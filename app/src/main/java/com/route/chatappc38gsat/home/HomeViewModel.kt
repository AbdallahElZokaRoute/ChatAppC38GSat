package com.route.chatappc38gsat.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.route.chatappc38gsat.database.getRoomsFromFirestoreDB
import com.route.chatappc38gsat.model.Room

class HomeViewModel : ViewModel() {
    var navigator: Navigator? = null
    val roomListState = mutableStateOf<List<Room>>(listOf())
    fun getRoomsList() {
        getRoomsFromFirestoreDB(onSuccessListener = {
            roomListState.value = it.toObjects(Room::class.java)
        }, onFailureListener = {
            Log.e("Error", "${it.localizedMessage}")
        })
    }

    fun navigateToAddRoomScreen() {
        navigator?.navigateToAddRoom()
    }
}