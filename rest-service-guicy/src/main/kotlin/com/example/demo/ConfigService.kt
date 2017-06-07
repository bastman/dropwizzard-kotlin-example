package com.example.demo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigService @Inject constructor(
        private val configuration: RestServiceConfiguration
) {
    val seb = configuration.sebTestConf
    val configGoogle = configuration.configGoogle
    val configPaypal = configuration.configPaypal
}