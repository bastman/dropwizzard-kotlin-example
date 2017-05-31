package com.example.demo.api.resources

import com.example.demo.service.BarService
import com.example.demo.service.FooService
import java.time.Instant
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/api")
class FooResource @Inject constructor(
        private val fooService: FooService
        , private val barService: BarService
) {

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/foo")
    fun foo(): Response {
        return Response.ok("/foo: ${fooService.foo()}").build()
    }


    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/bar")
    fun bar(): Response {
        return Response.ok("/bar: ${barService.bar()}").build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/data/{id}")
    fun getData(@PathParam("id") id: String): Response {
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

