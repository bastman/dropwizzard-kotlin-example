package com.example.demo.service

import com.google.inject.ImplementedBy

@ImplementedBy(FooServiceImpl::class)
interface FooService {
    fun foo(): String
}