package enfasys.android.core.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResultTest {
    @Test
    fun `Creates a success result`() {
        val value = 1
        val result = Result.success(value)

        assertEquals(value, result.value)
    }

    @Test
    fun `Creates a success empty result`() {
        val result = Result.success()

        assertEquals(Unit, result.value)
    }

    @Test
    fun `Create a failure from failureDescription`() {
        val failureDescription = FailureDescription.NetworkConnectionError

        val result = Result.fail(failureDescription)

        assertEquals(failureDescription, result.failureDescription)
    }

    @Test
    fun `Create a failure from throwable`() {
        val throwable = NoDataInServerResponseError
        val failure = FailureDescription.WithThrowable(throwable)

        val result = Result.fail(NoDataInServerResponseError)

        assertEquals(failure, result.failureDescription)
    }

    @Test
    fun `Create a failure from a previous failure`() {
        val failure = Result.fail(NoDataInServerResponseError)

        val newFailure = Result.fail(failure)

        assertEquals(failure, newFailure)
    }

    @Test
    fun `isFailure returns false when result is success`() {
        val value = 1
        val result = Result.Success(value)

        assertFalse(result.isFailure())
    }

    @Test
    fun `isFailure returns true when result is failure`() {
        val result = Result.fail(NoMessageInServerResponseError)

        assertTrue(result.isFailure())
    }
}
