package com.example.demo

import com.example.demo.domain.pizza.paypal.PaypalClient
import com.example.demo.resources.ConfigResource
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.When
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import javax.inject.Inject
import javax.ws.rs.core.Response


class ConfigResourceTestMockito {

    @Inject
    lateinit var configService:ConfigService
    @Inject
    lateinit var configExample:ConfigExample
    @Inject
    lateinit var configPaypal:ConfigPaypal
    @Inject
    lateinit var configGoogle:ConfigGoogle

    @Inject
    lateinit var paypalClient:PaypalClient

    @InjectMocks
    lateinit var apiResource:ConfigResource

    @Before
    fun setup() {
        configService = mock()
        configExample = spy(ConfigExample(test = "example-spy"))
        //configPaypal= mock()
       // When calling configPaypal itReturns ConfigPaypal(apiKey = "paypal-apikey-mock")

        configPaypal = spy(ConfigPaypal(apiKey = "paypal-apikey-spy"))
        configGoogle = spy(ConfigGoogle(apiKey = "google-apikey-spy"))
        paypalClient = spy(PaypalClient(paypal = configPaypal))

        MockitoAnnotations.initMocks(this)
        apiResource shouldBeInstanceOf ConfigResource::class
    }

    @Test
    fun testFoo() {
        apiResource shouldBeInstanceOf ConfigResource::class

        When calling configService.provideConfigGoogle() itReturns ConfigGoogle(apiKey = "google-api-key-stubbed")
        When calling configService.configGoogle itReturns ConfigGoogle(apiKey = "configService:google-api-key-stubbed")
        When calling configService.configPaypal itReturns ConfigPaypal(apiKey = "configService:paypal-api-key-stubbed")


        val response: Response = apiResource.foo()

        response.entity `should be instance of` Map::class

        val responseData: Map<String, Any?> = response.entity as Map<String, Any?>

        println(responseData)
    }
}