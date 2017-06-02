package com.example.demo.service

import com.example.demo.jdbi.Tweet
import com.example.demo.jdbi.TweetRepository
import javax.inject.Inject


class TweetService @Inject constructor(val tweetRepository: TweetRepository) {

    fun save(tweet:Tweet) {
        tweetRepository.add(tweet)
    }
}
