package com.example.demo.payments

import com.example.demo.payments.google.CreditCardProcessorGoogle
import com.example.demo.payments.paypal.BankTransferProcessorPaypal
import com.example.demo.payments.paypal.CreditCardProcessorPaypal
import javax.inject.Inject

class CreditCardProcessorService @Inject constructor(
    val google: CreditCardProcessorGoogle,
    val paypal: CreditCardProcessorPaypal
) {
    fun suggestProcessor(): CreditCardProcessor {
        return google
    }
}
class BankTransferProcessorService @Inject constructor(
    val paypal: BankTransferProcessorPaypal
) {
    fun suggestProcessor(): BankTransferProcessor {
        return paypal
    }
}