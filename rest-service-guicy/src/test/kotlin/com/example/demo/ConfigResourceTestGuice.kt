package com.example.demo

import com.example.demo.resources.ConfigResource
import com.example.demo.service.BarService
import com.google.inject.Guice
import com.google.inject.Provides
import com.google.inject.util.Modules
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule
import javax.inject.Singleton
import javax.ws.rs.core.Response


internal class ConfigResourceTestGuice {


    var configServiceStub: ConfigService = {
        val stub = mock(ConfigService::class)
        When calling stub.provideConfigGoogle() itReturns ConfigGoogle(apiKey = "google-api-key-stubbed-provideConfigGoogle")
        When calling stub.configGoogle itReturns ConfigGoogle(apiKey = "google-api-key-stubbed-configGoogle")

        stub
    }.invoke()


    // a test module, that binds BarService (Interface) to a MockImplementation
    internal class TestModule(val configServiceStub: ConfigService) : DropwizardAwareModule<RestServiceConfiguration>() {
        override fun configure() {

            bind(BarService::class.java)
                    .to(BarServiceMockImpl::class.java)
                    .asEagerSingleton() // bind to interface

            // does not work :(
            //When calling configServiceStub.provideConfigGoogle() itReturns ConfigGoogle(apiKey = "google-api-key-stubbed")

            // bind(ConfigService::class.java).to(configServiceStub)
            //  When calling configServiceStub.provideConfigGoogle() itReturns ConfigGoogle(apiKey = "google-api-key-stubbed")

        }

        @Provides @Singleton
        fun provideConfigService(): ConfigService {
            return configServiceStub
        }

        @Provides @Singleton
        fun provideConfigExample(): ConfigExample {
            return ConfigExample(test = "example-test-mocked")
        }

        @Provides @Singleton
        fun provideConfigPaypal(): ConfigPaypal {
            return ConfigPaypal(apiKey = "paypal-apikey-mocked")
        }

        @Provides @Singleton
        fun provideConfigGoogle(): ConfigGoogle {
            return ConfigGoogle(apiKey = "google-apikey-mocked")
        }
    }

    val injector = Guice.createInjector(
            // the default module
            Modules.override(RestServiceModule())
                    // will be modified by TestModule
                    .with(TestModule(configServiceStub))
    )
    val apiResource: ConfigResource = injector.getInstance(
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
        // Verify that a method with any parameter was called
        val mock = mock(ConfigService::class)
        When calling mock.provideConfigGoogle() itReturns ConfigGoogle(apiKey = "google-api-key-stubbed")

        val confGoogle = mock.provideConfigGoogle()
        Verify on mock that mock.provideConfigGoogle() was called


        val response: Response = apiResource.foo()

        response.entity `should be instance of` Map::class

        val responseData: Map<String, Any?> = response.entity as Map<String, Any?>

        println(responseData)
    }


}