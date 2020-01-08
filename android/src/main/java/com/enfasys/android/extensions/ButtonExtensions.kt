package com.enfasys.android.extensions

import android.graphics.Paint
import android.widget.Button
import androidx.annotation.StringRes

fun Button.setUnderlineText(@StringRes text: Int) {
    setText(text)
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun Button.setUnderlineText(text: String) {
    setText(text)
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}
