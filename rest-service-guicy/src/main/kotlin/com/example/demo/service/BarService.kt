package com.example.demo.service

import com.google.inject.ImplementedBy

@ImplementedBy(BarServiceImpl::class)
interface BarService {
    fun bar(): String
}