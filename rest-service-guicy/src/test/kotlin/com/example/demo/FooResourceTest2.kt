package com.example.demo

import com.example.demo.api.resources.FooResource
import com.example.demo.api.resources.GetDataResponse
import com.example.demo.service.BarService
import com.example.demo.service.FooService
import com.example.demo.service.FooServiceImpl
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.testing.fieldbinder.Bind
import com.google.inject.testing.fieldbinder.BoundFieldModule
import com.google.inject.util.Modules
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import javax.ws.rs.core.Response

// showcase how to replace an interface binding by ...
/*
internal class FooResourceTest2 {

    // bind(Bar.class).toInstance(barMock);
    @Bind private lateinit var barMock: BarServiceMockImpl

    // Foo depends on Bar.
    @Inject private lateinit var foo: FooService


    private val injector = Guice.createInjector(RestServiceModule())

    // FooResource depends on everything.
    private lateinit var apiResource: FooResource

    init {
        println("$this: init")
    }

    @Before
    fun setupTest() {
        barMock = BarServiceMockImpl(
            fooService = FooServiceImpl()
        )
        Guice.createInjector(
                RestServiceModule(),
                BoundFieldModule.of(this)
        ).injectMembers(this);

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
*/