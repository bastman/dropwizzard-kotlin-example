package com.example.demo.resources

import com.example.demo.ConfigExample
import com.example.demo.ConfigGoogle
import com.example.demo.ConfigPaypal
import com.example.demo.ConfigService
import com.example.demo.domain.pizza.paypal.PaypalClient
import com.example.demo.logging.AppLogger
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/config")
@Api("/config")
class ConfigResource @Inject constructor(
        private val configService: ConfigService,
        private val configExample: ConfigExample,
        private val configPaypal: ConfigPaypal,
        private val paypalClient: PaypalClient,
        private val configGoogle: ConfigGoogle
) {

    private val LOGGER = AppLogger.get(this::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/describe")
    @ApiOperation(
            value = "",
            notes = "",
            response = Map::class
    )
    fun foo(): Response {
        LOGGER.info("/foo $this")

        val responseData = mapOf<String, Any?>(
                "configExample" to configExample,
                "configPaypal" to configPaypal,
                "configGoogle" to configGoogle,
                "paypalClient.apiKey" to paypalClient.getApiKey(),
                "configService.configPaypal" to configService.configPaypal,
                "configService.configGoogle" to configService.configGoogle,
                "configService.providerConfigGoogle" to configService.provideConfigGoogle()
        )

        return Response.ok(responseData).build()
    }
}