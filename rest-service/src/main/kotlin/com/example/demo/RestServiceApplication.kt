package com.example.demo

import com.example.demo.api.resources.FooResource
import com.example.demo.logging.AppLogger
import com.example.demo.service.FooServiceImpl
import io.dropwizard.Application
import io.dropwizard.setup.Environment
import org.glassfish.hk2.utilities.binding.AbstractBinder




class RestServiceApplication: Application<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)


    override fun run(configuration: RestServiceConfiguration, environment: Environment) {
        LOGGER.info("run()")
        //environment.jersey().register(FooResource())

        val fooService = FooServiceImpl()
        //val fooResource = FooResource(fooService=fooService)

        environment.jersey().register(object : AbstractBinder() {
            override fun configure() {
               // bind(FooResource::class)

                bind(configuration).to(RestServiceConfiguration::class.java)
                bind(fooService).to(FooServiceImpl::class)
            }
        })
       // environment.jersey()

        environment.jersey().register(FooResource(fooService=fooService))


    }


}