package com.example.demo.service

import com.example.demo.logging.AppLogger
import javax.inject.Singleton

@Singleton
class FooServiceImpl : FooService {
    private val LOGGER = AppLogger.get(this::class.java)

    override fun foo(): String {
        LOGGER.info("foo() $this")
        return "$this.foo()"
    }
}