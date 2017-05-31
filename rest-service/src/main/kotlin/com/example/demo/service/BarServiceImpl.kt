package com.example.demo.service

import com.example.demo.logging.AppLogger
import org.jvnet.hk2.annotations.Service
import javax.inject.Inject
import javax.inject.Singleton

//@BindingAnnotation
//annotation class Bar1

@Service
@Singleton
class BarServiceImpl @Inject constructor(
        //@Foo1
        private val fooService: FooService
) : BarService {
    private val LOGGER = AppLogger.get(this::class.java)


    override fun bar(): String {
        return "$this.bar(): ${fooService.foo()}"
    }

}