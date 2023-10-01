package com.route.chatappc38gsat.model

import com.route.chatappc38gsat.R

data class Category(
    val categoryId: String? = null,
    val name: String? = null,
    val imageId: Int? = null
) {
    companion object {
        const val MUSIC = "MUSIC"
        const val MOVIES = "MOVIES"
        const val SPORTS = "SPORTS"
        fun fromId(categoryId: String?): Category {
            return when (categoryId) {
                MUSIC -> Category(MUSIC, "Music", R.drawable.music)
                MOVIES -> Category(MOVIES, "Movies", R.drawable.movies)
                else -> Category(SPORTS, "Sports", R.drawable.sports)
            }
        }

        fun getCategoriesList(): List<Category> {
            return listOf(fromId(MUSIC), fromId(SPORTS), fromId(MOVIES))
        }

    }
}
