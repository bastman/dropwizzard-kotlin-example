package com.example.demo.api.resources

import com.example.demo.domain.pizza.Psp
import com.example.demo.domain.pizza.Transaction
import com.example.demo.domain.pizza.google.GoogleCheckoutService
import com.example.demo.domain.pizza.paypal.PaypalService
import com.example.demo.logging.AppLogger
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/pizza")
@Api("/pizza")
@Singleton
class PizzaResource @Inject constructor(
        private val paypalService: PaypalService,
        private val googleCheckoutService: GoogleCheckoutService
) {

    private val LOGGER = AppLogger.get(this::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/psp/providers")
    //@ApiResponse(code = 200, message = "OK", response = PspProvidersResponse::class)
    @ApiOperation(
            value = "list psp",
            notes = "list payment providers available",
            response = PspProvidersResponse::class
    )
    fun listPspProviders(): Response {
        LOGGER.info("/psp/providers $this")
        val payload = PspProvidersResponse(
                providers = listOf(
                    PspProvidersResponse.Psp(
                            id = paypalService.psp.id,
                            description = paypalService.psp.description,
                            apiKey = paypalService.client.getApiKey()
                    ) ,
                        PspProvidersResponse.Psp(
                                id = googleCheckoutService.psp.id,
                                description = googleCheckoutService.psp.description,
                                apiKey = googleCheckoutService.client.getApiKey()
                        )
                )
        )

        return Response.ok(payload).build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/psp/{id}/pay/{amount}")
    @ApiOperation(
            value = "pay",
            notes = "pay with a payment provider",
            response = PspPayResponse::class
    )
    fun pay(
            @PathParam("id") id: String,
            @PathParam("amount") amount: Int
    ): Response {
        LOGGER.info("/psp/$id/pay $this")

        val psp:Psp? = Psp.values().find { it.id==id }
        if(psp==null) {
            val pspIds = Psp.values().map { it.id }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid psp id! Must be one of: ${pspIds}")
                    .build()
        }

        val transaction=when(psp) {
            Psp.Paypal -> {
                paypalService.pay(amount)
            }
            Psp.GoogleCheckout -> {
                googleCheckoutService.pay(amount)
            }
        }

        val payload = PspPayResponse(
                transaction = transaction
        )

        return Response.ok(payload).build()
    }
}


data class PspProvidersResponse(
        val providers:List<Psp>
) {
    data class Psp(val id:String, val description:String, val apiKey:String)
}

data class PspPayResponse(
        val transaction:Transaction
)