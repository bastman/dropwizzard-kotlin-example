package com.example.demo.domain.pizza

interface BillingService{
    val psp:Psp
    val processor:CreditCardProcessor
    val transactionLog:TransactionLog
}

interface CreditCardProcessor


