package enfasys.android.impl

import androidx.lifecycle.ViewModel
import com.freeletics.coredux.subscribeToChangedStateUpdates
import enfasys.android.core.DispatcherGroup
import enfasys.android.impl.mvi.Action
import enfasys.android.impl.mvi.StateMachine
import enfasys.android.impl.mvi.MviViewModel
import enfasys.android.impl.mvi.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel<A : Action, VS : ViewState>(
    protected val stateMachine: StateMachine<A, VS>,
    protected val dispatcherGroup: DispatcherGroup
) : MviViewModel<A, VS>, ViewModel(), CoroutineScope {
    protected val statesChannel = ConflatedBroadcastChannel<VS>()

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherGroup.forIO

    override fun states(): Flow<VS> =
        statesChannel.asFlow()
            .distinctUntilChanged()
            .flowOn(coroutineContext)

    override fun process(action: A) {
        stateMachine.dispatch(action)
    }

    override fun onCleared() {
        cancel()
        super.onCleared()
    }

    protected open fun init() {
        stateMachine.createStore(this).subscribeToChangedStateUpdates {
            statesChannel.offer(it)
        }
    }
}
