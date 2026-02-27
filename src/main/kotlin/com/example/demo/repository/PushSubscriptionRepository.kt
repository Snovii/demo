package com.example.demo.repository

import com.example.demo.model.PushSubscription
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PushSubscriptionRepository :
    MongoRepository<PushSubscription, String>

