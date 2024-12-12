package org.example.charts

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform