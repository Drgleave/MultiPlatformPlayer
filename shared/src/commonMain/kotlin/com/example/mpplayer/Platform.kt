package com.example.mpplayer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform