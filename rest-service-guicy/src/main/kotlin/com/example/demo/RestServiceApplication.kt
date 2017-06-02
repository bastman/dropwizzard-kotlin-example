package com.example.demo

import com.example.demo.api.resources.FooResource
import com.example.demo.logging.AppLogger
//import com.hubspot.dropwizard.guice.GuiceBundle
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import ru.vyarus.dropwizard.guice.GuiceBundle

class RestServiceApplication : Application<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)

    override fun initialize(bootstrap: Bootstrap<RestServiceConfiguration>) {
        super.initialize(bootstrap)

        bootstrap.addBundle(GuiceBundle.builder<RestServiceConfiguration>()
                .enableAutoConfig(javaClass.`package`.name)
                // enable @WebFilter
                .useWebInstallers()
                .modules(RestServiceModule())
                // optional: enable automatic registration of dropwizard bundles
                // - e.g.: diagnostics bundle
                .configureFromDropwizardBundles()
                .printDiagnosticInfo()


                .build())
    }

    override fun run(configuration: RestServiceConfiguration, environment: Environment) {
        LOGGER.info("run()")

        //environment.jersey().register(FooResource::class.java)
        // environment.lifecycle().manage(guiceBundle.injector.getInstance(TemplateHealthCheck::class.java))
    }
}