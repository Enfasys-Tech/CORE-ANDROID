package enfasys.android.impl.mvi

import android.widget.ProgressBar
import enfasys.android.impl.BaseViewModel
import enfasys.android.impl.R

abstract class MviActivityWithProgressBar<Action : MviAction, ViewState : MviViewState, ViewModel : BaseViewModel<Action, ViewState>> :
    MviActivity<Action, ViewState, ViewModel>() {
    protected val progressBar: ProgressBar by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<ProgressBar>(R.id.core_progressbar)
    }
}