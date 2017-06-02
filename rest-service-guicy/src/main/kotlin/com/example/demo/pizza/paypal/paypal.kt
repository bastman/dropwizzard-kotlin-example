package com.example.demo.domain.pizza.paypal

import com.example.demo.domain.pizza.*
import com.example.demo.logging.AppLogger
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayPalCreditCardProcessor:CreditCardProcessor {
    private val LOGGER = AppLogger.get(this::class.java)
    fun pay(amount:Int) {
        LOGGER.info("pay() amount=$amount")
    }
}
@Singleton
class PaypalService @Inject constructor(
        override val processor: PayPalCreditCardProcessor,
        override val transactionLog: TransactionLog
):BillingService {
    private val LOGGER = AppLogger.get(this::class.java)
    override val psp: Psp = Psp.Paypal

    fun pay(amount:Int):Transaction {
        LOGGER.info("pay() amount=$amount")

        processor.pay(amount)

        val transaction= Transaction(
                psp = psp,
                status = TransactionStatus.PAID,
                amount = amount,
                modifiedAt = Instant.now()
        )
        transactionLog.save(transaction)

        return transaction
    }

}
