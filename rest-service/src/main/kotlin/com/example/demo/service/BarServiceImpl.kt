package com.example.demo.service

import com.example.demo.logging.AppLogger
import org.jvnet.hk2.annotations.Service
import javax.inject.Inject
import javax.inject.Singleton

@Service
@Singleton
class BarServiceImpl @Inject constructor(
        private val fooService: FooService
) : BarService {
    private val LOGGER = AppLogger.get(this::class.java)


    override fun bar(): String {
        return "$this.bar(): ${fooService.foo()}"
    }

}