package enfasys.android.core.usecase

import kotlin.contracts.contract

fun <T : Any> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Result.Failure)
        returns(false) implies (this@isFailure is Result.Success)
    }
    return this@isFailure is Result.Failure
}

sealed class Result<out R> {
    data class Success<out R>(val value: R) : Result<R>()
    data class Failure(val failureDescription: FailureDescription) : Result<Nothing>()

    companion object {
        fun <R> success(value: R) = Success(value)
        fun success() = Success(Unit)
        fun fail(failureDescription: FailureDescription) = Failure(failureDescription)
        fun fail(throwable: Throwable) = Failure(FailureDescription.WithThrowable(throwable))
        fun fail(failure: Failure) = Failure(failure.failureDescription)
    }
}
