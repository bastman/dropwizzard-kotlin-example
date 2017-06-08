package com.example.demo.payments.google


import com.example.demo.ConfigGoogle
import com.example.demo.payments.CreditCardProcessor
import javax.inject.Inject

class CreditCardProcessorGoogle
    @Inject constructor(val config: ConfigGoogle): CreditCardProcessor {
    override fun capture() {
        //println(config)
    }
}
