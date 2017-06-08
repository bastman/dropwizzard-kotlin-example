package com.example.demo.payments.paypal


import com.example.demo.ConfigGoogle
import com.example.demo.ConfigPaypal
import com.example.demo.payments.BankTransferProcessor
import com.example.demo.payments.CreditCardProcessor
import javax.inject.Inject


class CreditCardProcessorPaypal
    @Inject constructor(val config: ConfigPaypal) : CreditCardProcessor {
    override fun capture() {
        //println(config)
    }
}
class BankTransferProcessorPaypal
    @Inject constructor(val config: ConfigPaypal) : BankTransferProcessor {
    override fun initBankTransfer() {
        //println(config)
    }
}
