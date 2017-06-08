package com.example.demo.resources

import com.example.demo.logging.AppLogger
import com.example.demo.payments.PaymentMethod
import com.example.demo.payments.PaymentResult
import com.example.demo.payments.PaymentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import java.util.Currency
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import org.funktionale.tries.Try
@Path("/payments")
@Api("/payments")
class PaymentResource @Inject constructor(
    val paymentService: PaymentService
){
    private val LOGGER = AppLogger.get(this::class.java)

    data class CurrencyRequest(val currencyCode: String)
    data class CurrencyResponse(val currency: Currency, val defaultFractionDigits:Int)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/foo")
    @ApiOperation(
        value = "list payment methods",
        notes = "list payment"
    )
    fun foo(
        req:CurrencyRequest
    ): Response {
        val c:Currency = Currency.getInstance(req.currencyCode)

        val payload = CurrencyResponse(
            currency = c,
            defaultFractionDigits = c.defaultFractionDigits
        )
        return Response.ok(payload)
            .build()
    }

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

        val request:Try<PaymentMethodsRequest.Validated> = Try{
            PaymentMethodsRequest.Validated(
                currency = Currency.getInstance(req.currency),
                amount = req.amount
            )
        }

        when(request) {
            is Try.Failure -> {
                val response:Map<String, Any?> = mapOf(
                    "error" to "some error: ${request.throwable.javaClass} message=${request.throwable.message}"
                )
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .build()
            }

            else -> {
                val requestValidated = request.get()

                val paymentMethods=paymentService.getPaymentMethods(
                    currency = requestValidated.currency
                )

                val response = PaymentMethodsResponse(
                    paymentMethods=paymentMethods
                )

                return Response.ok(response)
                    .build()
            }
        }
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

        val requestValidated = PayRequest.Validated(
            paymentMethod = req.paymentMethod,
            currency = Currency.getInstance(req.currency),
            amount = req.amount
        )

        val result=paymentService.pay(
            paymentMethod = requestValidated.paymentMethod,
            currency = requestValidated.currency,
            amount = requestValidated.amount
        )

        val response = PayResponse(
            status= result.status,
            currency = result.currency.currencyCode,
            amount = result.amount
        )

        return Response.ok(response)
            .build()
    }
}

data class PaymentMethodsRequest(val currency:String, val amount:Double) {
    data class Validated(val currency:Currency, val amount:Double)
}
data class PaymentMethodsResponse(val paymentMethods:List<PaymentMethod>)

data class PayRequest(
    val currency:String, val amount:Double, val paymentMethod: PaymentMethod
) {
    data class Validated(
        val currency:Currency, val amount:Double, val paymentMethod: PaymentMethod
    )
}
data class PayResponse(
    val currency:String, val amount:Double, val status: PaymentResult.Status
)