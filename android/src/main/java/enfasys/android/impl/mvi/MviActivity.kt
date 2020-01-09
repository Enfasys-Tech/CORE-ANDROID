package enfasys.android.impl.mvi

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import enfasys.android.core.DispatcherGroup
import enfasys.android.core.Logger
import enfasys.android.impl.*
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
abstract class MviActivity<Action : MviAction, ViewState : MviViewState, ViewModel : BaseViewModel<Action, ViewState>> :
    BaseActivity(),
    MviView<Action, ViewState>, CoroutineScope {
    @Inject
    protected lateinit var dispatcherGroup: DispatcherGroup

    @Inject
    protected lateinit var logger: Logger

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ViewModel>
    protected val viewModel: ViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory)
            .get(getViewModel())
    }

    protected abstract fun getViewModel(): Class<ViewModel>

    override val coroutineContext: CoroutineContext
        get() = Job() + dispatcherGroup.UI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
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