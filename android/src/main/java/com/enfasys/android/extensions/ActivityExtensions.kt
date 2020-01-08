package com.enfasys.android.extensions

import android.content.Intent
import android.os.Bundle

inline fun <reified T> getObject(
    key: String,
    intent: Intent,
    savedInstanceState: Bundle?
): T? {
    var result: T? = null

    val extras = intent.extras
    if (extras != null) {
        val obj = extras.get(key)
        if (obj != null) {
            if (obj is T) {
                result = obj
            }
        }
    }

    if (result == null) {
        if (savedInstanceState != null) {
            val obj = savedInstanceState.get(key)
            if (obj != null) {
                if (obj is T) {
                    result = obj
                }
            }
        }
    }

    return result
}
