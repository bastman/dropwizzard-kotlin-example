package com.example.demo

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.inject.Inject
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull
import io.dropwizard.Configuration as DropwizardConfiguration


class RestServiceConfiguration(
        @Valid @NotNull @JsonProperty("database")
        val database: DataSourceFactory = DataSourceFactory(),
        @JsonProperty("defaultName")
        val defaultName: String = "Stranger",
        @Valid @NotNull @JsonProperty("configPaypal")
        val configPaypal: ConfigPaypal,
        @Valid @NotNull @JsonProperty("configGoogle")
        val configGoogle: ConfigGoogle


) : io.dropwizard.Configuration() {

}


data class ConfigPaypal @Inject constructor(
        val apiKey: String
)

data class ConfigGoogle @Inject constructor(
        val apiKey: String
)
