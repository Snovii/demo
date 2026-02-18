package com.example.demo.model

data class PushSubscription(
    val endpoint: String,
    val keys: Keys
)

data class Keys(
    val p256dh: String,
    val auth: String
)
