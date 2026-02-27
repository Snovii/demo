package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "subscriptions")

data class PushSubscription(
    @Id
    val endpoint: String,
    val keys: Keys
)

data class Keys(
    val p256dh: String,
    val auth: String
)
