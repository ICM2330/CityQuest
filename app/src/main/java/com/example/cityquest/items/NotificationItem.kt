package com.example.cityquest.items

data class NotificationItem(
    val title: String,
    val message: String,
    val isRead: Boolean,
    val timeAgo: String
)
