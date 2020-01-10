package enfasys.android.core

import enfasys.android.core.usecase.FailureDescription
import enfasys.android.core.usecase.NoDataInServerResponseError
import enfasys.android.core.usecase.NoMessageInServerResponseError
import enfasys.android.core.usecase.Result

@Suppress("MemberVisibilityCanBePrivate")
interface BaseRepository {
    val logger: Logger
    val networkVerifier: NetworkVerifier

    suspend fun <T> safeCall(block: suspend () -> NetworkResponse<out T>): Result<T> {
        return try {
            val response = block()
            if (response.success) {
                if (response.data != null) {
                    Result.success(response.data)
                } else {
                    logger.e(NoDataInServerResponseError)
                    Result.fail(NoDataInServerResponseError)
                }
            } else {
                if (response.message != null) {
                    Result.fail(FailureDescription.WithFeature(response.message))
                } else {
                    logger.e(NoMessageInServerResponseError)
                    Result.fail(NoMessageInServerResponseError)
                }
            }
        } catch (ex: Exception) {
            if (networkVerifier.isConnectionAvailable()) {
                logger.e(ex)
                Result.fail(ex)
            } else {
                Result.fail(FailureDescription.NetworkConnectionError)
            }
        }
    }

    suspend fun safeEmptyCall(block: suspend () -> NetworkResponse<Empty>): Result<Unit> {
        return try {
            val response = block()
            if (response.success) {
                Result.success()
            } else {
                if (response.message != null) {
                    Result.fail(FailureDescription.WithFeature(response.message))
                } else {
                    logger.e(NoMessageInServerResponseError)
                    Result.fail(NoMessageInServerResponseError)
                }
            }
        } catch (ex: Exception) {
            if (networkVerifier.isConnectionAvailable()) {
                logger.e(ex)
                Result.fail(ex)
            } else {
                Result.fail(FailureDescription.NetworkConnectionError)
            }
        }
    }
}
