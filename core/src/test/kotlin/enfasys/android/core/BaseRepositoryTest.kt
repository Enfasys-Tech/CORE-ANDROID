package enfasys.android.core

import enfasys.android.core.usecase.FailureDescription
import enfasys.android.core.usecase.NoDataInServerResponseError
import enfasys.android.core.usecase.NoMessageInServerResponseError
import enfasys.android.core.usecase.Result
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BaseRepositoryTest {
    private val data = 322
    private val message = "Error"

    @Test
    fun `safeCall returns success when response data is different from null and response is success`() =
        runBlocking {
            val response = NetworkResponse(success = true, data = data, message = null)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeCall { response }

            assertTrue(result is Result.Success)
            assertEquals(Result.success(data), result)
            assertFalse(logger.wasCalled)
        }

    @Test
    fun `safeCall returns failure when response data is null and response is success`() =
        runBlocking {
            val response = NetworkResponse(success = true, data = null, message = null)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeCall { response }

            assertTrue(result is Result.Failure)
            assertEquals(
                FailureDescription.WithThrowable(NoDataInServerResponseError),
                result.failureDescription
            )
            assertTrue(logger.wasCalled)
        }

    @Test
    fun `safeCall returns failure when response is not success and there is a non-null message`() =
        runBlocking {
            val response = NetworkResponse(success = false, data = null, message = message)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeCall { response }

            assertTrue(result is Result.Failure)
            assertEquals(FailureDescription.WithFeature(message), result.failureDescription)
            assertFalse(logger.wasCalled)
        }

    @Test
    fun `safeCall returns failure when response is not success and there is a null message`() =
        runBlocking {
            val response = NetworkResponse(success = false, data = null, message = null)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeCall { response }

            assertTrue(result is Result.Failure)
            assertEquals(
                FailureDescription.WithThrowable(NoMessageInServerResponseError),
                result.failureDescription
            )
            assertTrue(logger.wasCalled)
        }

    @Test
    fun `safeCall returns failure and logs exception when block() throws an exception and there is a connection available`() =
        runBlocking {
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeCall<Int> { throw Exception(message) }

            assertTrue(result is Result.Failure)
            assertTrue(result.failureDescription is FailureDescription.WithThrowable)
            assertTrue(logger.wasCalled)
        }

    @Test
    fun `safeCall returns failure when block() throws an exception and there is not a connection available`() =
        runBlocking {
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(false)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeCall<Int> { throw Exception() }

            assertTrue(result is Result.Failure)
            assertEquals(FailureDescription.NetworkConnectionError, result.failureDescription)
            assertFalse(logger.wasCalled)
        }

    @Test
    fun `safeEmptyCall returns success when response is success`() =
        runBlocking {
            val response = NetworkResponse<Empty>(success = true, data = null, message = null)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeEmptyCall { response }

            assertTrue(result is Result.Success)
            assertEquals(Result.success(), result)
            assertFalse(logger.wasCalled)
        }

    @Test
    fun `safeEmptyCall returns failure when response is not success and there is a non-null message`() =
        runBlocking {
            val response = NetworkResponse<Empty>(success = false, data = null, message = message)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeEmptyCall { response }

            assertTrue(result is Result.Failure)
            assertEquals(FailureDescription.WithFeature(message), result.failureDescription)
            assertFalse(logger.wasCalled)
        }

    @Test
    fun `safeEmptyCall returns failure when response is not success and there is a null message`() =
        runBlocking {
            val response = NetworkResponse<Empty>(success = false, data = null, message = null)
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeEmptyCall { response }

            assertTrue(result is Result.Failure)
            assertEquals(
                FailureDescription.WithThrowable(NoMessageInServerResponseError),
                result.failureDescription
            )
            assertTrue(logger.wasCalled)
        }

    @Test
    fun `safeEmptyCall returns failure and logs exception when block() throws an exception and there is a connection available`() =
        runBlocking {
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(true)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeEmptyCall { throw Exception(message) }

            assertTrue(result is Result.Failure)
            assertTrue(result.failureDescription is FailureDescription.WithThrowable)
            assertTrue(logger.wasCalled)
        }

    @Test
    fun `safeEmptyCall returns failure when block() throws an exception and there is not a connection available`() =
        runBlocking {
            val logger = FakeLogger()
            val networkVerifier = FakeNetworkVerifier(false)
            val repository = FakeBaseRepository(logger, networkVerifier)

            val result = repository.safeEmptyCall { throw Exception() }

            assertTrue(result is Result.Failure)
            assertEquals(FailureDescription.NetworkConnectionError, result.failureDescription)
            assertFalse(logger.wasCalled)
        }
}
