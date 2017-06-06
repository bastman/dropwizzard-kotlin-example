package com.example.demo.jdbi

import org.skife.jdbi.v2.sqlobject.*
import ru.vyarus.guicey.jdbi.installer.repository.JdbiRepository
import ru.vyarus.guicey.jdbi.tx.InTransaction

@JdbiRepository
@InTransaction
interface TweetRepository {
    @GetGeneratedKeys

    @SqlUpdate("INSERT INTO tweets (tweetId, message, modifiedAt) VALUES (:tweetId, :message" +
            ", UTC_TIMESTAMP(3))")
            /*
    @SqlUpdate("INSERT INTO tweets (tweetId, message, modifiedAt) VALUES (:tweetId, :message" +
            ", :modifiedAt)")
            */
    fun add(@BindBean tweet: Tweet): Int

    @SqlUpdate("update tweets set message=:message, modifiedAt=:modifiedAt where tweetId=:tweetId")
    fun update(@BindBean tweet: Tweet): Int

    @SqlQuery("select * from tweets where tweetId = :tweetId")
    fun findById(@Bind("tweetId") tweetId: Int): Tweet

    @SqlQuery("select * from tweets")
    fun findAll(): List<Tweet>
}