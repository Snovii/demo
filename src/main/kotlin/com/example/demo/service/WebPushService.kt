package com.example.demo.service

import jakarta.annotation.PostConstruct
import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Security

@Service
class WebPushService(
    @Value("\${push.vapid.public-key}")
    private val publicKey: String,

    @Value("\${push.vapid.private-key}")
    private val privateKey: String
) {

    private lateinit var pushService: PushService

    @PostConstruct
    fun init() {
        Security.addProvider(BouncyCastleProvider())
        pushService = PushService()
            .setPublicKey(publicKey)
            .setPrivateKey(privateKey)
            .setSubject("mailto:test@example.com")
    }

    fun sendNotification(
        endpoint: String,
        p256dh: String,
        auth: String,
        payload: String
    ) {
        val notification = Notification(endpoint, p256dh, auth, payload)
        pushService.send(notification)
    }

    fun getPublicKey(): String {
        return publicKey
    }
}
