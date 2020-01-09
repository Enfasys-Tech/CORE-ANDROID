package enfasys.android.impl.extensions

import android.os.Bundle

inline fun <reified T> getObject(
    key: String,
    arguments: Bundle?,
    savedInstanceState: Bundle?
): T? {
    var result: T? = null

    if (arguments != null) {
        val obj = arguments.get(key)
        if (obj != null) {
            if (obj is T) {
                result = obj
            }
        }
    }

    if (result == null) {
        if (savedInstanceState != null) {
            val obj = savedInstanceState.get(key)
            if (obj != null) {
                if (obj is T) {
                    result = obj
                }
            }
        }
    }

    return result
}
