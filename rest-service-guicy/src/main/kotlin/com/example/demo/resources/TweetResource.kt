package com.example.demo.resources

import com.example.demo.jdbi.Tweet
import com.example.demo.jdbi.TweetAddRequest
import com.example.demo.logging.AppLogger
import com.example.demo.service.TweetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import ru.vyarus.guicey.jdbi.tx.InTransaction
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/tweet")
@Api("/tweet")
@Singleton
class TweetResource @Inject constructor(val tweetService: TweetService){

    private val LOGGER = AppLogger.get(this::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/add")
    @ApiOperation(
            value = "add a tweet",
            notes = "",
            response = TweetResponse::class
    )
    @InTransaction
    fun save(
            //tw:Tweet
            req: TweetCreateRequest
    ): Response {

        LOGGER.info("/tweet/add $this request=$req")

        val tweetId = tweetService.add(
                TweetAddRequest(
                        message = req.message,
                        modifiedAt = Instant.now()
                )
        )
        val tweet = tweetService.findById(id = tweetId)

        return Response.ok(TweetResponse(tweet=tweet)).build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/{id}")
    @ApiOperation(
            value = "get tweet by id",
            notes = "",
            response = TweetResponse::class
    )
    fun getById(
            @PathParam("id") id: Int
    ): Response {
        LOGGER.info("/tweet/$id $this")

        val tweet=tweetService.findById(id)

        return Response.ok(TweetResponse(tweet=tweet)).build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/list")
    @ApiOperation(
            value = "get all tweets",
            notes = "",
            response = TweetCollectionResponse::class
    )
    fun findAll(): Response {
        LOGGER.info("/tweet/list $this")

        val tweets= tweetService.findAll()

        return Response.ok(TweetCollectionResponse(tweets=tweets)).build()
    }
}

data class TweetCollectionResponse(val tweets:List<Tweet>)
data class TweetResponse(val tweet:Tweet?)
data class TweetCreateRequest(val message: String)