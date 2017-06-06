package com.example.demo

import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import io.dropwizard.Configuration as DropwizardConfiguration
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.inject.Inject
import com.google.inject.ProvidedBy
import com.google.inject.Provides
import javax.validation.constraints.NotNull



class RestServiceConfiguration(
        @Valid @NotNull @JsonProperty("database")
        val database:DataSourceFactory = DataSourceFactory(),
        @JsonProperty("defaultName")
        val defaultName: String="Stranger",
        @Valid @NotNull @JsonProperty("configPaypal")
        val configPaypal: ConfigPaypal,
        @Valid @NotNull @JsonProperty("configGoogle")
        val configGoogle: ConfigGoogle


) : io.dropwizard.Configuration() {

}


data class ConfigPaypal @Inject constructor(
        val apiKey:String
)
data class ConfigGoogle @Inject constructor(
        val apiKey:String
)

//class PaypalConfigService()