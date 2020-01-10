package enfasys.android.impl

import androidx.lifecycle.ViewModel
import com.freeletics.coredux.subscribeToChangedStateUpdates
import enfasys.android.core.DispatcherGroup
import enfasys.android.impl.mvi.StateMachine
import enfasys.android.impl.mvi.MviViewModel
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
abstract class BaseViewModel<Action : enfasys.android.impl.mvi.Action, ViewState : enfasys.android.impl.mvi.ViewState>(
    protected val stateMachine: StateMachine<Action, ViewState>,
    protected val dispatcherGroup: DispatcherGroup
) : MviViewModel<Action, ViewState>, ViewModel(), CoroutineScope {
    protected val statesChannel = ConflatedBroadcastChannel<ViewState>()

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherGroup.forIO

    override fun states(): Flow<ViewState> =
        statesChannel.asFlow()
            .distinctUntilChanged()
            .flowOn(coroutineContext)

    override fun process(action: Action) {
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
