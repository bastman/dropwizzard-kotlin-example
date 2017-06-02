package com.example.demo

import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import io.dropwizard.Configuration as DropwizardConfiguration

data class RestServiceConfiguration(
        @Valid val database: DataSourceFactory
        ) : DropwizardConfiguration() {
    fun getFooo():String{
        return "FOOOO"
    }
}