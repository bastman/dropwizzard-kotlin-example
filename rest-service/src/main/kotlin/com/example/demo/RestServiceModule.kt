package com.example.demo

import com.example.demo.service.BarService
import com.example.demo.service.BarServiceImpl
import com.example.demo.service.FooService
import com.example.demo.service.FooServiceImpl
import com.google.inject.AbstractModule


class RestServiceModule : AbstractModule() {
    override fun configure() {

        // works
        //bind(FooService::class.java).toInstance(FooServiceImpl())

        // works
        bind(FooServiceImpl::class.java) // register
        bind(FooService::class.java).to(FooServiceImpl::class.java) // bind to interface

        bind(BarServiceImpl::class.java) // register
        bind(BarService::class.java).to(BarServiceImpl::class.java) // bind to interface

    }
}

