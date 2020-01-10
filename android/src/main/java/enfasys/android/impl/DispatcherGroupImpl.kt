package enfasys.android.impl

import enfasys.android.core.DispatcherGroup
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DispatcherGroupImpl @Inject constructor() : DispatcherGroup {
    override val forIO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val forComputation: CoroutineDispatcher
        get() = Dispatchers.Default
    override val forUI: CoroutineDispatcher
        get() = Dispatchers.Main
    override val forState: CoroutineDispatcher
        get() = (Executors.newSingleThreadExecutor() as Executor).asCoroutineDispatcher()
}
