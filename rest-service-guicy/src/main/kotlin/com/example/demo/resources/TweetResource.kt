package com.example.demo.resources

import com.example.demo.api.resources.PspProvidersResponse
import com.example.demo.domain.pizza.Psp
import com.example.demo.jdbi.Tweet
import com.example.demo.logging.AppLogger
import com.example.demo.service.TweetService
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/tweet")
@Singleton
class TweetResource @Inject constructor(val tweetService: TweetService){

    private val LOGGER = AppLogger.get(this::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/save")
    fun save(): Response {
        LOGGER.info("/tweet/save $this")

        val tweet = Tweet(
                tweetId = 3,
                message = "aaa",
                modifiedAt = Instant.now()
        )
        tweetService.save(tweet)

        return Response.ok(tweet).build()
    }
}
