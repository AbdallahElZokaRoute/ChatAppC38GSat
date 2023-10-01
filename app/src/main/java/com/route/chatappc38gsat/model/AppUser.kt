package com.route.chatappc38gsat.model

data class AppUser(
    val uid: String? = null,
    val firstName: String? = null,
    val email: String? = null,
) {
    companion object {
        const val COLLECTION_NAME = "Users"

    }
}
