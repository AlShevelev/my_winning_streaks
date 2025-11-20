package com.shevelev.mywinningstreaks

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform