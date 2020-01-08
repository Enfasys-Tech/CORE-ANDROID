package com.enfasys.core

interface Logger {
    fun e(throwable: Throwable, tag: String? = null)
    fun d(message: String, tag: String? = null)
}