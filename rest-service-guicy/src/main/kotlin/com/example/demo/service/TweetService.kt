package com.example.demo.service

import com.example.demo.jdbi.Tweet
import com.example.demo.jdbi.TweetAddRequest
import com.example.demo.jdbi.TweetRepository
import javax.inject.Inject


class TweetService @Inject constructor(
        val tweetRepository: TweetRepository
) {

    fun add(tweet: TweetAddRequest): Int {
        return tweetRepository.add(tweet)
    }

    fun findById(id: Int): Tweet? {
        val tweet = tweetRepository.findById(id)

        return tweet
    }

    fun findAll(): List<Tweet> {
        return tweetRepository.findAll()
    }
}
