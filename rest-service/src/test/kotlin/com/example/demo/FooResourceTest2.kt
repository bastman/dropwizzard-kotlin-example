package com.example.demo

import com.example.demo.api.resources.FooResource
import com.example.demo.api.resources.GetDataResponse
import com.example.demo.service.BarService
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.util.Modules
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import javax.ws.rs.core.Response

// showcase how to replace an interface binding by ...
internal class FooResourceTest2 {

    // bind(Bar.class).toInstance(barMock);
    //@Bind private val barMock: Bar? = null

    // Foo depends on Bar.
    //@Inject private val foo: Foo? = null


    // a test module, that binds BarService (Interface) to a MockImplementation
    internal class TestModule : AbstractModule() {
        override fun configure() {
            //bind(BarServiceMockImpl::class.java) // register
            bind(BarService::class.java)
                    .to(BarServiceMockImpl::class.java)
                    .asEagerSingleton() // bind to interface
        }
    }

    private val injector = Guice.createInjector(
            // the default module
            Modules.override(RestServiceModule())
                    // will be modified by TestModule
                    .with(TestModule())
    )
    private val apiResource: FooResource = injector.getInstance(
            FooResource::class.java
    )

    init {
        println("$this: init")
    }

    @Before
    fun setupTest() {
        apiResource shouldBeInstanceOf FooResource::class
    }

    @Test
    fun `api getData(id) should return data with expected id`() {
        val id = "1-2-3";

        val response: Response = apiResource.getData(id = id)

        response.entity `should be instance of` GetDataResponse::class

        val responseData: GetDataResponse = response.entity as GetDataResponse

        responseData.id `should equal` id
    }

    @Test
    fun `api getBar() should return a string that contains the name of mocked BarService`() {
        val response: Response = apiResource.bar()

        response.entity `should be instance of` String::class

        val responseData: String = response.entity as String

        responseData `should contain` "BarServiceMockImpl"
    }

}