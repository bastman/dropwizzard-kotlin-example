package com.example.demo

import com.example.demo.service.BarService
import com.example.demo.service.FooService
import javax.inject.Inject


class BarServiceMockImpl @Inject constructor(
        private val fooService: FooService
) : BarService {

    override fun bar(): String {
        return "$this.bar(): MOCKED !!!! ${fooService.foo()}"
    }
}