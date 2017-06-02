package com.example.demo.api.resources

import com.example.demo.domain.pizza.Psp
import com.example.demo.domain.pizza.Transaction
import com.example.demo.domain.pizza.google.GoogleCheckoutService
import com.example.demo.domain.pizza.paypal.PaypalService
import com.example.demo.logging.AppLogger
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/pizza")
@Singleton
class PizzaResource @Inject constructor(
        private val paypalService: PaypalService,
        private val googleCheckoutService: GoogleCheckoutService
) {

    private val LOGGER = AppLogger.get(this::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/psp/providers")
    fun listPspProviders(): Response {
        LOGGER.info("/psp/providers $this")
        val payload = PspProvidersResponse(
                providers = Psp.values().map { it.id }
        )

        return Response.ok(payload).build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/psp/{id}/pay/{amount}")
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
        val providers:List<String>
)

data class PspPayResponse(
        val transaction:Transaction
)