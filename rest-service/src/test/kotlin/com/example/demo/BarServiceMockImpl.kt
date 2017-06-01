package com.example.demo

import com.example.demo.logging.AppLogger
import com.example.demo.service.BarService
import com.example.demo.service.FooService
import javax.inject.Inject


class BarServiceMockImpl @Inject constructor(
        private val fooService: FooService
) : BarService {
    private val LOGGER = AppLogger.get(this::class.java)

    override fun bar(): String {
        LOGGER.info("bar() $this")

        return "$this.bar(): MOCKED !!!! ${fooService.foo()}"
    }
}