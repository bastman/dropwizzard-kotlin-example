package com.example.demo.domain.pizza.google

import com.example.demo.ConfigGoogle
import com.example.demo.ConfigPaypal
import com.example.demo.RestServiceConfiguration
import com.example.demo.domain.pizza.*
import com.example.demo.logging.AppLogger
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleCheckoutClient(private val googleConfig: ConfigGoogle)  {

    @com.google.inject.Inject constructor(config: RestServiceConfiguration): this(googleConfig = config.configGoogle)

    fun getApiKey():String = googleConfig.apiKey
}
@Singleton
class GoogleCheckoutProcessor: CreditCardProcessor {
    private val LOGGER = AppLogger.get(this::class.java)
    fun pay(amount:Int) {
        LOGGER.info("pay() amount=$amount")
    }
}

@Singleton
class GoogleCheckoutService@Inject constructor(
        override val processor: GoogleCheckoutProcessor,
        override val transactionLog: TransactionLog
):BillingService{
    private val LOGGER = AppLogger.get(this::class.java)
    override val psp:Psp = Psp.GoogleCheckout

    fun pay(amount:Int):Transaction {
        LOGGER.info("pay() amount=$amount")

        processor.pay(amount)

        val transaction=Transaction(
                psp = psp,
                status = TransactionStatus.PAID,
                amount = amount,
                modifiedAt = Instant.now()
        )
        transactionLog.save(transaction)

        return transaction
    }
}
