package com.route.chatappc38gsat.home

import com.route.chatappc38gsat.model.Room

interface Navigator {
    fun navigateToAddRoom()
    fun navigateToChatActivity(room: Room)
}
