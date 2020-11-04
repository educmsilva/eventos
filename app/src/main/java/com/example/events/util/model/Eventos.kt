package com.example.events.util.model

data class Eventos(
    val people: List<Any?>,
    val date: Long,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String,
    val id: String
)