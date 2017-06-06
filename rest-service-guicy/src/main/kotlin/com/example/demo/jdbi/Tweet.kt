package com.example.demo.jdbi

import java.time.Instant
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet

data class Tweet(
        val tweetId: Int,
        val message: String,
        val modifiedAt: Instant
)

class TweetMapper : ResultSetMapper<Tweet> {
    override fun map(index: Int, r: ResultSet, ctx: StatementContext?): Tweet {
        return Tweet(tweetId= r.getInt("tweetId"), message = r.getString("message"), modifiedAt = r.getTimestamp("modifiedAt").toInstant())
    }
}