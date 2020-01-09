package enfasys.android.impl.mvi

import android.widget.ProgressBar
import enfasys.android.impl.BaseViewModel
import enfasys.android.impl.R

abstract class MviFragmentWithProgressBar<Action : MviAction, ViewState : MviViewState, ViewModel : BaseViewModel<Action, ViewState>> :
    MviFragment<Action, ViewState, ViewModel>() {
    protected val progressBar: ProgressBar by lazy(LazyThreadSafetyMode.NONE) {
        requireView().findViewById<ProgressBar>(R.id.core_progressbar)
    }
}