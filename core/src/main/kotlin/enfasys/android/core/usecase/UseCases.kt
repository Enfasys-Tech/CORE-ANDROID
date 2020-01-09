package enfasys.android.core.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in Params, out R>

interface FlowableUseCase<in Params, out R> : UseCase<Params, R> {
    suspend fun execute(params: Params): Flow<R>
}

interface FlowableUseCaseWithoutParams<out R> : UseCase<Unit, R> {
    suspend fun execute(): Flow<R>
}

interface SuspendableUseCase<in Params, out R> : UseCase<Params, R> {
    suspend fun execute(params: Params): Result<R>
}

interface SuspendableUseCaseWithoutParams<out R> : UseCase<Unit, R> {
    suspend fun execute(): Result<R>
}

interface NormalUseCase<in Params, out R> : UseCase<Params, R> {
    fun execute(params: Params): R
}

interface NormalUseCaseWithoutParams<out R> : UseCase<Unit, R> {
    fun execute(): R
}