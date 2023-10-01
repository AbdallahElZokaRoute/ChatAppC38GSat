package com.route.chatappc38gsat.model

data class Room(
    var id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val categoryId: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "Rooms"
    }
}
