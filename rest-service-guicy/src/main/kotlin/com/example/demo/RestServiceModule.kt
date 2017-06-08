package com.example.demo

import com.example.demo.logging.AppLogger
import com.google.inject.Provides
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule
import javax.inject.Singleton

class RestServiceModule : DropwizardAwareModule<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)

    override fun configure() {
        val configuration = configuration() // access configuration
        val environment = environment() // access environment
        val bootstrap = bootstrap()  // access dropwizard bootstrap
        val binder = binder()

        LOGGER.info("+++++++ configure() ++++++++")
        LOGGER.info("binder=$binder")
        LOGGER.info("configuration=$configuration")
        LOGGER.info("environment=$environment")
        LOGGER.info("bootstrap=$bootstrap")
        LOGGER.info("++++++++++++++++++++")

        // strict mode
        binder.disableCircularProxies();
        binder.requireExactBindingAnnotations();
        //binder.requireExplicitBindings();

        // works: binding to concrete instance
        //bind(FooService::class.java).toInstance(FooServiceImpl())

        // works:
        //bind(FooServiceImpl::class.java) // register
        //bind(FooService::class.java).to(FooServiceImpl::class.java) // bind to interface

        // works:
        //bind(BarServiceImpl::class.java) // register
        //bind(BarService::class.java).to(BarServiceImpl::class.java) // bind to interface

        //bind(PaypalClient::class.java).to(PaypalClient(config = configuration.configPaypal))

    }

    @Provides @Singleton
    fun provideConfigExample(configuration: RestServiceConfiguration): SebTestConf = configuration.configExample

    @Provides @Singleton
    fun provideConfigPaypal(configuration: RestServiceConfiguration): ConfigPaypal = configuration.configPaypal

}

