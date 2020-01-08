package com.enfasys.core

import com.enfasys.core.usecase.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseRepository(
    protected val logger: Logger,
    protected val networkVerifier: NetworkVerifier
) {
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