package com.example.demo.controller

import com.example.demo.service.WebPushService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/push")
class PushController(

    private val webPushService: WebPushService
) {

    @GetMapping("/public-key")
    fun getPublicKey(): String {
        return webPushService.getPublicKey()
    }

    @PostMapping("/send")
    fun sendPush(): String {

        println("🚀 Send endpoint hit")

        val subscriptions = webPushService.getAllSubscriptions()

        if (subscriptions.isEmpty()) {
            println("❌ No subscriptions found")
            return "No subscriptions found"
        }

        val payload = """
            {
                "title": "Hello from Kotlin",
                "message": "Your push system works!"
            }
        """.trimIndent()

        subscriptions.forEach {
            webPushService.sendNotification(it, payload)
        }

        return "Push triggered for ${subscriptions.size} subscription(s)"
    }
}