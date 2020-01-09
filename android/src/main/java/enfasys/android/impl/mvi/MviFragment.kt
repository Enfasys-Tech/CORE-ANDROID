package enfasys.android.impl.mvi

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import enfasys.android.core.DispatcherGroup
import enfasys.android.core.Logger
import enfasys.android.impl.BaseFragment
import enfasys.android.impl.BaseViewModel
import enfasys.android.impl.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
abstract class MviFragment<Action : MviAction, ViewState : MviViewState, ViewModel : BaseViewModel<Action, ViewState>> :
    BaseFragment(), MviView<Action, ViewState>, CoroutineScope {
    @Inject
    protected lateinit var dispatcherGroup: DispatcherGroup

    @Inject
    protected lateinit var logger: Logger

    override val coroutineContext: CoroutineContext
        get() = Job() + dispatcherGroup.UI

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ViewModel>
    protected val viewModel: ViewModel by lazy(LazyThreadSafetyMode.NONE) {
        if (isTheirOwnViewModel()) {
            ViewModelProviders.of(this, viewModelFactory)
                .get(getViewModel())
        } else {
            ViewModelProviders.of(requireActivity(), viewModelFactory)
                .get(getViewModel())
        }
    }

    protected abstract fun getViewModel(): Class<ViewModel>

    protected open fun isTheirOwnViewModel(): Boolean = true

    open fun onVisible(): Unit = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    protected fun isVisibleForTheUser(): Boolean {
        return isAdded && isResumed && isResumed
    }

    private fun bind() {
        launch {
            viewModel.states()
                .distinctUntilChanged()
                .onCompletion { if (it != null) logger.e(it) }
                .collect { render(it) }
        }
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}