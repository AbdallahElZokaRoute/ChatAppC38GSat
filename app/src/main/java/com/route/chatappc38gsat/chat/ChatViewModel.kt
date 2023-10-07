package com.route.chatappc38gsat.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.route.chatappc38gsat.database.addMessageToFirestoreDB
import com.route.chatappc38gsat.database.getMessagesFromFirestoreDB
import com.route.chatappc38gsat.model.DataUtils
import com.route.chatappc38gsat.model.Message
import com.route.chatappc38gsat.model.Room
import java.util.Date

class ChatViewModel : ViewModel() {
    val messageFieldState = mutableStateOf("")
    var room: Room? = null
    val messagesListState = mutableStateOf<List<Message>>(listOf())
    fun addMessageToFirestore() {
        if (messageFieldState.value.isEmpty() || messageFieldState.value.isBlank())
            return
        val message = Message(
            senderName = DataUtils.appUser?.firstName,
            senderId = DataUtils.appUser?.uid,
            dateTime = Date().time,
            roomId = room?.id,
            content = messageFieldState.value
        )
        addMessageToFirestoreDB(message, onSuccessListener = {
            messageFieldState.value = ""
        }, onFailureListener = {
            Log.e("Tag", "${it.message}")
        })
    }

    fun listenToMessagesChanges() {
        getMessagesFromFirestoreDB(roomId = room?.id!!) { snapshots, ex ->
            if (ex != null) {
                Log.e("Tag", "${ex.message}")
                return@getMessagesFromFirestoreDB
            }
            val newMessageList = mutableListOf<Message>()
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        newMessageList.add(dc.document.toObject(Message::class.java))
                    }
//                    DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
//                    DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
                    else -> {

                    }
                }
            }
            newMessageList.addAll(messagesListState.value)
            messagesListState.value = newMessageList

        }
    }
}