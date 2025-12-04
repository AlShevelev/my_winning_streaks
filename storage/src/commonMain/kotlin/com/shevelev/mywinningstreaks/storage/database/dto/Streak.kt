package com.shevelev.mywinningstreaks.storage.database.dto

data class Streak(
    val id: Long,
    val sortingOrder: Int,
    val title: String,
    val marked: Boolean,
)