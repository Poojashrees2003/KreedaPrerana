package com.example.kreedaprerana.model

data class Badge(
    val id: String,            // ✅ FIXED (String → Int)
    val title: String,
    val description: String,
    val unlocked: Boolean
)