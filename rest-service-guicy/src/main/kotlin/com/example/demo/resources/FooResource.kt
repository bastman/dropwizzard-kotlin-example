package com.example.demo.api.resources

import com.example.demo.logging.AppLogger
import com.example.demo.service.BarService
import com.example.demo.service.FooService
import io.swagger.annotations.Api
import java.time.Instant
import javax.inject.Inject
import javax.inject.Provider
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.servlet.http.HttpServletRequest




@Path("/api")
@Api("/api")
class FooResource @Inject constructor(
        private val fooService: FooService,
        private val barService: BarService
) {
    private val LOGGER = AppLogger.get(this::class.java)

    @Inject
    private lateinit var requestProvider: Provider<HttpServletRequest>

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/foo")
    fun foo(): Response {
        LOGGER.info("/foo $this")
        return Response.ok("/foo: ${fooService.foo()}").build()
    }


    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/bar")
    fun bar(): Response {
        val servletRequest = requestProvider.get()
        val ip = servletRequest.getRemoteAddr();
        LOGGER.info("/bar $this: your ip: $ip")
        return Response.ok("/bar: ${barService.bar()} - your ip: $ip").build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/data/{id}")
    fun getData(@PathParam("id") id: String): Response {
        LOGGER.info("/data/$id $this")
        val data = GetDataResponse(
                id = id,
                foo = fooService.foo(),
                bar = barService.bar(),
                modifiedAt = Instant.now()
        )
        return Response.ok(data).build()
    }

}

data class GetDataResponse(
        val id: String,
        val foo: String,
        val bar: String,
        val modifiedAt: Instant
)

