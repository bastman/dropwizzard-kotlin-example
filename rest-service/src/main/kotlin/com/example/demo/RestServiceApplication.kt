package com.example.demo

import com.example.demo.logging.AppLogger
import io.dropwizard.Application
import io.dropwizard.setup.Environment


class RestServiceApplication: Application<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)


    override fun run(configuration: RestServiceConfiguration, environment: Environment) {
        LOGGER.info("run()")
    }
}