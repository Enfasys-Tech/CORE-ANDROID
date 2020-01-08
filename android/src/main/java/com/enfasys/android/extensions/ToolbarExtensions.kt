package com.enfasys.android.extensions

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.enfasys.android.R

fun Fragment.setToolbar(@StringRes title: Int) {
    val activity = requireActivity() as AppCompatActivity
    activity.supportActionBar?.apply {
        setTitle(title)
    }
}

fun AppCompatActivity.setToolbar(@StringRes title: Int, shouldGoBack: Boolean) {
    val toolbar: Toolbar = findViewById(R.id.core_toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(shouldGoBack)
        setTitle(title)
    }
    if (shouldGoBack) {
        toolbar.setNavigationOnClickListener { finish() }
    }
}

fun AppCompatActivity.setToolbar(title: String, shouldGoBack: Boolean) {
    val toolbar: Toolbar = findViewById(R.id.core_toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(shouldGoBack)
        setTitle(title)
    }
    if (shouldGoBack) {
        toolbar.setNavigationOnClickListener { finish() }
    }
}