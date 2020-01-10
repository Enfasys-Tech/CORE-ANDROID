package enfasys.android.impl.mvi

import androidx.lifecycle.ViewModelProviders
import enfasys.android.core.DispatcherGroup
import enfasys.android.core.Logger
import enfasys.android.impl.BaseFragment
import enfasys.android.impl.BaseViewModel
import enfasys.android.impl.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
abstract class MviFragment<A : Action, VS : ViewState, VM : BaseViewModel<A, VS>> :
    BaseFragment(), MviView<A, VS>, CoroutineScope {
    @Inject
    protected lateinit var dispatcherGroup: DispatcherGroup

    @Inject
    protected lateinit var logger: Logger

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherGroup.forUI

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>
    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        if (isTheirOwnViewModel()) {
            ViewModelProviders.of(this, viewModelFactory)
                .get(getViewModel())
        } else {
            ViewModelProviders.of(requireActivity(), viewModelFactory)
                .get(getViewModel())
        }
    }

    protected abstract fun getViewModel(): Class<VM>

    protected open fun isTheirOwnViewModel(): Boolean = true

    protected fun isVisibleForTheUser(): Boolean {
        return isAdded && isResumed && isResumed
    }

    open fun onVisible(): Unit = Unit

    override fun onStart() {
        super.onStart()
        bind()
    }

    override fun onStop() {
        cancel()
        super.onStop()
    }

    private fun bind() {
        launch {
            viewModel.states()
                .distinctUntilChanged()
                .onCompletion { if (it != null) logger.e(it) }
                .collect { render(it) }
        }
    }
}
