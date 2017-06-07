package com.example.demo.jdbi

import java.time.Instant

data class Tweet(
        val tweetId: Int,
        val message: String,
        val modifiedAt: Instant
)

