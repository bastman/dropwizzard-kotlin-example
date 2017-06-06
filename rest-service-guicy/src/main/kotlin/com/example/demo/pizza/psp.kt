package com.example.demo.domain.pizza

enum class Psp(val id: String, val description: String) {
    Paypal(id = "paypal", description = "paypal psp description"),
    GoogleCheckout(id = "google", description = "google checkout psp description")
    ;

}
