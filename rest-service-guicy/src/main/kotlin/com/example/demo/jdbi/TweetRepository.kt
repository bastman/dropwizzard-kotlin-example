package com.example.demo.jdbi

import org.skife.jdbi.v2.sqlobject.BindBean
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlUpdate
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
}