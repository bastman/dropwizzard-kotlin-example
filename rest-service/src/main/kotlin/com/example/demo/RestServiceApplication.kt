package com.example.demo

import com.example.demo.api.resources.FooResource
import com.example.demo.logging.AppLogger
import com.hubspot.dropwizard.guice.GuiceBundle
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class RestServiceApplication : Application<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)

    private lateinit var guiceBundle: GuiceBundle<RestServiceConfiguration>

    override fun initialize(bootstrap: Bootstrap<RestServiceConfiguration>) {
        super.initialize(bootstrap)

        guiceBundle = GuiceBundle.newBuilder<RestServiceConfiguration>()
                .addModule(RestServiceModule())
                .enableAutoConfig(javaClass.`package`.name)
                .setConfigClass(RestServiceConfiguration::class.java)
                .build()

        bootstrap.addBundle(guiceBundle)
    }

    override fun run(configuration: RestServiceConfiguration, environment: Environment) {
        LOGGER.info("run()")

        environment.jersey().register(FooResource::class.java)
        // environment.lifecycle().manage(guiceBundle.injector.getInstance(TemplateHealthCheck::class.java))
    }
}