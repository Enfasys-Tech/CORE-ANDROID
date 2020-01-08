package com.enfasys.android.extensions

import android.content.Intent
import android.os.Bundle
import com.enfasys.android.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ActivityExtensionsTest : BaseTest() {
    private val key = "key"

    private val firstValue = 1
    private val secondValue = 2

    private val unknownValue = "Unknown"

    @Test
    fun `When key is not found on intent and savedInstanceState, it returns null`() {
        val intent = Intent()
        val bundle = Bundle()

        val result = getObject<Int>(key, intent, bundle)

        assertNull(result)
    }

    @Test
    fun `When key is found in intent, it returns it`() {
        val intent = Intent()
        intent.putExtra(key, firstValue)
        val bundle = Bundle()

        val result = getObject<Int>(key, intent, bundle)

        assertNotNull(result)
        assertEquals(firstValue, result)
    }

    @Test
    fun `When key is found in savedInstanceState, it returns it`() {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putInt(key, firstValue)

        val result = getObject<Int>(key, intent, bundle)

        assertNotNull(result)
        assertEquals(firstValue, result)
    }

    @Test
    fun `When key is found first in intent and then in savedInstanceState, it returns the value from intent`() {
        val intent = Intent()
        intent.putExtra(key, firstValue)
        val bundle = Bundle()
        bundle.putInt(key, secondValue)

        val result = getObject<Int>(key, intent, bundle)

        assertNotNull(result)
        assertEquals(firstValue, result)
    }

    @Test
    fun `When key is found first in intent (but it is not the desired type) and then in savedInstanceState, it returns the value from savedInstanceState`() {
        val intent = Intent()
        intent.putExtra(key, unknownValue)
        val bundle = Bundle()
        bundle.putInt(key, secondValue)

        val result = getObject<Int>(key, intent, bundle)

        assertNotNull(result)
        assertEquals(secondValue, result)
    }
}