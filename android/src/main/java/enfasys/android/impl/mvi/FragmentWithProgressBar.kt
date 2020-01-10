package enfasys.android.impl.mvi

import android.widget.ProgressBar
import enfasys.android.impl.BaseViewModel
import enfasys.android.impl.R

abstract class FragmentWithProgressBar<A : Action, VS : ViewState, VM : BaseViewModel<A, VS>> :
    MviFragment<A, VS, VM>() {
    protected val progressBar: ProgressBar by lazy(LazyThreadSafetyMode.NONE) {
        requireView().findViewById<ProgressBar>(R.id.core_progressbar)
    }
}
