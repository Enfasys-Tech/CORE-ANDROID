package enfasys.android.impl.mvi

import com.freeletics.coredux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface Action

interface ViewState

interface MviView<A : Action, VS : ViewState> {
    fun render(state: VS)
}

interface MviViewModel<A : Action, VS : ViewState> {
    fun process(action: A)
    fun states(): Flow<VS>
}

interface StateMachine<A : Action, VS : ViewState> {
    fun createStore(scope: CoroutineScope): Store<VS, A>
    fun dispatch(action: A)
}
