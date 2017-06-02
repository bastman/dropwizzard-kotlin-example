package com.example.demo

import com.example.demo.logging.AppLogger
import io.dropwizard.lifecycle.Managed

class RestServiceBootstrap : Managed {
    private val LOGGER = AppLogger.get(this::class.java)
    override fun start() {
        LOGGER.info("start()")
    }

    override fun stop() {
        LOGGER.info("stop()")
    }
}