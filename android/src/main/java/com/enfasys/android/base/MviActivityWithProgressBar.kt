package com.enfasys.android.base

import android.widget.ProgressBar
import com.enfasys.android.R

abstract class MviActivityWithProgressBar<Action : MviAction, ViewState : MviViewState, ViewModel : BaseViewModel<Action, ViewState>> :
    MviActivity<Action, ViewState, ViewModel>() {
    protected val progressBar: ProgressBar by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<ProgressBar>(R.id.core_progressbar)
    }
}