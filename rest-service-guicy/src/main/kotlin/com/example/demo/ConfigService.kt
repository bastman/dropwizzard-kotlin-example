package com.example.demo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigService @Inject constructor(
        private val configuration: RestServiceConfiguration
) {
    val configExample = configuration.configExample
    val configGoogle = configuration.configGoogle
    val configPaypal = configuration.configPaypal
    //@Provides //does not work
    fun provideConfigGoogle(): ConfigGoogle {
        return configGoogle
    }
}