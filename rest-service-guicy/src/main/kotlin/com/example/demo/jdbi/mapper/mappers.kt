package com.example.demo.jdbi.mapper

import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.Argument
import org.skife.jdbi.v2.tweak.ArgumentFactory
import java.sql.Timestamp
import java.time.Instant


class InstantAsSqlTimestampArgument : ArgumentFactory<Instant> {

    override fun accepts(expectedType: Class<*>, value: Any?, ctx: StatementContext): Boolean {
        return value != null && Instant::class.java.isAssignableFrom(value.javaClass)
    }

    override fun build(expectedType: Class<*>, value: Instant, ctx: StatementContext): Argument {
        val ts = Timestamp.from(value)

        val r = Argument { position, statement, ctx -> statement.setTimestamp(position, ts) }

        return r
    }
}