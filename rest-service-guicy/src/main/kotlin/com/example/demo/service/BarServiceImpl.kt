package com.example.demo.service

import com.example.demo.logging.AppLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarServiceImpl @Inject constructor(
        private val fooService: FooService
) : BarService {
    private val LOGGER = AppLogger.get(this::class.java)

    override fun bar(): String {
        LOGGER.info("bar() $this")

        return "$this.bar(): ${fooService.foo()}"
    }

}