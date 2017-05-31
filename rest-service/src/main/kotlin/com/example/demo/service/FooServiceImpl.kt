package com.example.demo.service

import org.jvnet.hk2.annotations.Service
import javax.inject.Singleton

@Service
@Singleton
class FooServiceImpl : FooService {
    override fun foo(): String {
        return "$this.foo()"
    }
}