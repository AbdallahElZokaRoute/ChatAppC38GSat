package com.route.chatappc38gsat.model

data class Message(
    var id: String? = null,
    val senderName: String? = null,
    val senderId: String? = null,
    val content: String? = null,
    val dateTime: Long? = null,
    val roomId: String? = null

) {
    companion object {
        const val COLLECTION_NAME = "Messages"
    }

}
