package com.enfasys.android.base

import android.widget.ProgressBar
import com.enfasys.android.R

abstract class MviFragmentWithProgressBar<Action : MviAction, ViewState : MviViewState, ViewModel : BaseViewModel<Action, ViewState>> :
    MviFragment<Action, ViewState, ViewModel>() {
    protected val progressBar: ProgressBar by lazy(LazyThreadSafetyMode.NONE) {
        requireView().findViewById<ProgressBar>(R.id.core_progressbar)
    }
}