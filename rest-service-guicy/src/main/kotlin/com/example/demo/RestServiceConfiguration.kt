package com.example.demo

import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import io.dropwizard.Configuration as DropwizardConfiguration
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

/*
class RestServiceConfiguration(
        @Valid val database: DataSourceFactory
        ) : DropwizardConfiguration() {

}
*/

class RestServiceConfiguration : io.dropwizard.Configuration() {

        @Valid
        @NotNull
        @JsonProperty
        val database = DataSourceFactory()
}