package com.enfasys.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias Empty = String?

@JsonClass(generateAdapter = true)
data class NetworkResponse<T>(
    @Json(name = "success") val success: Boolean,
    @Json(name = "message") val message: String?,
    @Json(name = "data") val data: T?
)