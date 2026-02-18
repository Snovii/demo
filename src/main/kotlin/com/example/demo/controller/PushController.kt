package com.example.demo.controller

import com.example.demo.model.PushSubscription
import com.example.demo.service.WebPushService
import org.springframework.web.bind.annotation.*

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
    fun sendPush(@RequestBody subscription: PushSubscription) {
        val payload = """
            {
                "title": "Hello from Kotlin",
                "message": "Your push system works!"
            }
            

        """.trimIndent()
        try {
            println("Sending push now")

            webPushService.sendNotification(
                subscription.endpoint,
                subscription.keys.p256dh,
                subscription.keys.auth,
                payload
            )

            println("Push sent successfully")

        } catch (e: Exception) {
            println("Push failed")
            e.printStackTrace()
        }
    }
}
