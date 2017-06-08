package com.example.demo.payments

import java.util.Currency
import javax.inject.Inject


enum class PaymentMethod() {
    CreditCard,
    BankTransfer
}
interface CreditCardProcessor {
    fun capture()
}
interface BankTransferProcessor {
    fun initBankTransfer()
}



class PaymentService @Inject constructor(
    val processorCreditCard: CreditCardProcessorService,
    val processorBankTransfer: BankTransferProcessorService
) {
    fun getPaymentMethods(currency:Currency):List<PaymentMethod> {
        return when(currency.currencyCode.toLowerCase()) {
            "usd" -> {
                listOf(
                    PaymentMethod.CreditCard,
                    PaymentMethod.BankTransfer
                )
            } else ->{
                listOf(
                    PaymentMethod.CreditCard
                )
            }
        }
    }

    fun pay(paymentMethod: PaymentMethod, currency: Currency, amount: Double):PaymentResult {
        val status = when(paymentMethod) {
            PaymentMethod.CreditCard -> {
                val processor = processorCreditCard.suggestProcessor()
                processor.capture()

                PaymentResult.Status.CREATED
            }
            PaymentMethod.BankTransfer -> {
                val processor = processorBankTransfer.suggestProcessor()
                processor.initBankTransfer()

                PaymentResult.Status.CREATED
            }
        }

        return PaymentResult(status=status, amount = amount, currency = currency)
    }
}



data class PaymentResult(
    val status: Status,
    val currency: Currency,
    val amount: Double
) {
    enum class Status() {
        CREATED
    }
}

