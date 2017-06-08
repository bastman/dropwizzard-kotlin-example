package com.example.demo.resources

import com.example.demo.logging.AppLogger
import com.example.demo.payments.PaymentMethod
import com.example.demo.payments.PaymentResult
import com.example.demo.payments.PaymentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/payments")
@Api("/payments")
class PaymentResource @Inject constructor(
    val paymentService: PaymentService
){
    private val LOGGER = AppLogger.get(this::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/methods")
    @ApiOperation(
        value = "list payment methods",
        notes = "list payment",
        response = PaymentMethodsResponse::class
    )
    fun listPaymentMethods(
        req: PaymentMethodsRequest
    ): Response {
        LOGGER.info("/method : req=$req")

        val paymentMethods=paymentService.getPaymentMethods(
            currency = req.currency
        )

        val response = PaymentMethodsResponse(
            paymentMethods=paymentMethods
        )

        return Response.ok(response)
            .build()
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/pay")
    @ApiOperation(
        value = "pay",
        notes = "",
        response = PayResponse::class
    )
    fun pay(
        req: PayRequest
    ): Response {
        LOGGER.info("/pay : req=$req")

        val result=paymentService.pay(
            paymentMethod = req.paymentMethod,
            currency = req.currency,
            amount = req.amount
        )

        val response = PayResponse(
            status= result.status,
            currency = result.currency,
            amount = result.amount
        )

        return Response.ok(response)
            .build()
    }
}

data class PaymentMethodsRequest(val currency:String, val amount:Double)
data class PaymentMethodsResponse(val paymentMethods:List<PaymentMethod>)

data class PayRequest(
    val currency:String, val amount:Double, val paymentMethod: PaymentMethod
)
data class PayResponse(
    val currency:String, val amount:Double, val status: PaymentResult.Status
)