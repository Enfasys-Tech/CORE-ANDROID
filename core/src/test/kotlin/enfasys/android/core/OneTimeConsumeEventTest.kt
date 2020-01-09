package enfasys.android.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class OneTimeConsumeEventTest {
    @Test
    fun `Returns non null content if it is the first time is handled`() {
        val content = 1
        val event = OneTimeConsumeEvent(content)

        val actualContent = event.getContentIfNotHandled()

        assertNotNull(actualContent)
        assertEquals(content, actualContent)
    }

    @Test
    fun `Returns null content after it has been handled`() {
        val content = 1
        val event = OneTimeConsumeEvent(content)

        val firstTime = event.getContentIfNotHandled()
        val secondTime = event.getContentIfNotHandled()

        assertNotNull(firstTime)
        assertNull(secondTime)
    }

    @Test
    fun `Peeks content returns the content`() {
        val content = 1
        val event = OneTimeConsumeEvent(content)

        assertEquals(content, event.peekContent())
    }
}