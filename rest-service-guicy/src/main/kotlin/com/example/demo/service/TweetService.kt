package com.example.demo.service

import com.example.demo.ConfigGoogle
import com.example.demo.ConfigPaypal

import com.example.demo.RestServiceConfiguration
import com.example.demo.domain.pizza.google.GoogleCheckoutClient
import com.example.demo.domain.pizza.paypal.PaypalClient
import com.example.demo.jdbi.Tweet
import com.example.demo.jdbi.TweetRepository
import javax.inject.Inject


class TweetService @Inject constructor(
        val tweetRepository: TweetRepository,
        val paypalClient: PaypalClient,
        val googleClient: GoogleCheckoutClient
) {

    fun save(tweet:Tweet):Int {
        return tweetRepository.add(tweet)
    }

    fun findById(id:Int):Tweet? {
        val tweet = tweetRepository.findById(id)

        return tweet
    }

    fun findAll():List<Tweet> {
        return tweetRepository.findAll()
    }
}
