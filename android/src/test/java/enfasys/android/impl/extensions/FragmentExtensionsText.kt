package enfasys.android.impl.extensions

import android.os.Bundle
import enfasys.android.impl.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FragmentExtensionsText : BaseTest() {
    private val key = "key"

    private val firstValue = 1
    private val secondValue = 2

    private val unknownValue = "Unknown"

    @kotlin.test.Test
    fun `When key is not found on arguments and savedInstanceState, it returns null`() {
        val arguments = Bundle()
        val savedInstanceState = Bundle()

        val result = getObject<Int>(key, arguments, savedInstanceState)

        assertNull(result)
    }

    @Test
    fun `When key is found in arguments, it returns it`() {
        val arguments = Bundle()
        arguments.putInt(key, firstValue)
        val savedInstanceState = Bundle()

        val result = getObject<Int>(key, arguments, savedInstanceState)

        assertNotNull(result)
        assertEquals(firstValue, result)
    }

    @Test
    fun `When key is found in savedInstanceState, it returns it`() {
        val arguments = Bundle()
        val savedInstanceState = Bundle()
        savedInstanceState.putInt(key, firstValue)

        val result = getObject<Int>(key, arguments, savedInstanceState)

        assertNotNull(result)
        assertEquals(firstValue, result)
    }

    @Test
    fun `When key is found first in arguments and then in savedInstanceState, it returns the value from arguments`() {
        val arguments = Bundle()
        arguments.putInt(key, firstValue)
        val savedInstanceState = Bundle()
        savedInstanceState.putInt(key, secondValue)

        val result = getObject<Int>(key, arguments, savedInstanceState)

        assertNotNull(result)
        assertEquals(firstValue, result)
    }

    @Test
    fun `When key is found first in arguments (but it is not the desired type) and then in savedInstanceState, it returns the value from savedInstanceState`() {
        val arguments = Bundle()
        arguments.putString(key, unknownValue)
        val savedInstanceState = Bundle()
        savedInstanceState.putInt(key, secondValue)

        val result = getObject<Int>(key, arguments, savedInstanceState)

        assertNotNull(result)
        assertEquals(secondValue, result)
    }
}
