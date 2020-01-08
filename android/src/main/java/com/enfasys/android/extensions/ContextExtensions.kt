package com.enfasys.android.extensions

import android.content.Context

fun Context.getAbsolutePath(): String {
    return filesDir.absolutePath
}