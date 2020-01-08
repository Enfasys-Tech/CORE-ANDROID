package com.enfasys.android.base

import androidx.lifecycle.ViewModel
import com.enfasys.core.DispatcherGroup
import com.freeletics.coredux.subscribeToChangedStateUpdates
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel<Action : MviAction, ViewState : MviViewState>(
    protected val stateMachine: MviStateMachine<Action, ViewState>,
    protected val dispatcherGroup: DispatcherGroup
) : MviViewModel<Action, ViewState>, ViewModel(), CoroutineScope {
    protected val statesChannel = ConflatedBroadcastChannel<ViewState>()

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherGroup.IO

    override fun states(): Flow<ViewState> = statesChannel.asFlow().distinctUntilChanged()

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