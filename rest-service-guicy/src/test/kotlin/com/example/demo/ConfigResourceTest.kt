package com.example.demo

import com.example.demo.resources.ConfigResource
import com.example.demo.service.BarService
import com.google.inject.Guice
import com.google.inject.Provides
import com.google.inject.util.Modules
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule
import javax.inject.Singleton
import javax.ws.rs.core.Response


internal class ConfigResourceTest {

    // a test module, that binds BarService (Interface) to a MockImplementation
    internal class TestModule : DropwizardAwareModule<RestServiceConfiguration>() {
        override fun configure() {
            //bind(BarServiceMockImpl::class.java) // register
            bind(BarService::class.java)
                    .to(BarServiceMockImpl::class.java)
                    .asEagerSingleton() // bind to interface
        }

        @Provides @Singleton
        fun provideConfigExample(): ConfigExample {
            return ConfigExample(test = "example-test-mocked")
        }

        @Provides @Singleton
        fun provideConfigPaypal(): ConfigPaypal {
            return ConfigPaypal(apiKey = "paypal-apikey-mocked")
        }
    }

    private val injector = Guice.createInjector(
            // the default module
            Modules.override(RestServiceModule())
                    // will be modified by TestModule
                    .with(TestModule())
    )
    private val apiResource: ConfigResource = injector.getInstance(
            ConfigResource::class.java
    )

    init {
        println("$this: init")
    }

    @Before
    fun setupTest() {
        apiResource shouldBeInstanceOf ConfigResource::class
    }

    @Test
    fun `api describe should return something useful`() {
        val response: Response = apiResource.foo()

        response.entity `should be instance of` Map::class

        val responseData: Map<String, Any?> = response.entity as Map<String, Any?>

        println(responseData)
    }


}