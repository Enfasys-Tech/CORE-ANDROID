package enfasys.android.core

import enfasys.android.core.RandomStringGenerator.Companion.DEFAULT_LENGTH
import org.junit.Test
import kotlin.test.assertEquals

class RandomStringGeneratorTest {
    @Test
    fun `Random string has the desired length`() {
        val generator = RandomStringGenerator()
        val length = 24

        val randomString = generator.generate(length)

        assertEquals(length, randomString.length)
    }

    @Test
    fun `Random string has the default length`() {
        val generator = RandomStringGenerator()

        val randomString = generator.generate()

        assertEquals(DEFAULT_LENGTH, randomString.length)
    }
}
