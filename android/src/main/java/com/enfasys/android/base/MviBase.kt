package com.enfasys.android.base

import com.freeletics.coredux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MviAction

interface MviViewState

interface MviView<A : MviAction, VS : MviViewState> {
    fun render(state: VS)
}

interface MviViewModel<A : MviAction, VS : MviViewState> {
    fun process(action: A)
    fun states(): Flow<VS>
}

interface MviStateMachine<A : MviAction, VS : MviViewState> {
    fun createStore(scope: CoroutineScope): Store<VS, A>
    fun dispatch(action: A)
}