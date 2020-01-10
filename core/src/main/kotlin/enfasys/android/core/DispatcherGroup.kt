package enfasys.android.core

import kotlinx.coroutines.CoroutineDispatcher

@Suppress("PropertyName")
interface DispatcherGroup {
    val forIO: CoroutineDispatcher
    val forComputation: CoroutineDispatcher
    val forUI: CoroutineDispatcher
    val forState: CoroutineDispatcher
}
