package com.example.demo.domain.pizza

import com.example.demo.logging.AppLogger
import com.google.inject.ImplementedBy
import java.time.Instant
import javax.inject.Singleton

@ImplementedBy(DatabaseTransactionLog::class)
interface TransactionLog {
    fun save(transaction: Transaction)
}

enum class TransactionStatus() {
    PAID,
    REFUNDED
    ;
}

data class Transaction(
        val psp: Psp,
        val status: TransactionStatus,
        val amount: Int,
        val modifiedAt: Instant
)

@Singleton
class DatabaseTransactionLog() : TransactionLog {
    private val LOGGER = AppLogger.get(this::class.java)
    override fun save(transaction: Transaction) {
        LOGGER.info("save() transaction=$transaction")
    }
}
