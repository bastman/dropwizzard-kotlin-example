package com.example.demo.api.resources

import java.time.Instant
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
class FooResource {

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/ping")
    fun ping(): Response {
        return Response.ok("pong").build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/data/{id}")
    fun getData(@PathParam("id") id:String): Response {
        return Response.ok(
                GetDataResponse(id=id, foo = "bar", modifiedAt = Instant.now())
        ).build()
    }
}

data class GetDataResponse(
        val id:String,
        val foo:String,
        val modifiedAt:Instant
)

