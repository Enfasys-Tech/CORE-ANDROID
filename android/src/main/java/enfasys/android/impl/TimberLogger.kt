package enfasys.android.impl

import enfasys.android.core.Logger
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimberLogger @Inject constructor() : Logger {
    override fun e(throwable: Throwable, tag: String?) {
        if (tag != null) {
            Timber.tag(tag).e(throwable)
        } else {
            Timber.e(throwable)
        }
    }

    override fun d(message: String, tag: String?) {
        if (tag != null) {
            Timber.tag(tag).d(message)
        } else {
            Timber.d(message)
        }
    }
}
