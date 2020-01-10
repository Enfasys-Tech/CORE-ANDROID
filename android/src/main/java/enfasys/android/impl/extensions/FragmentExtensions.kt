package enfasys.android.impl.extensions

import android.os.Bundle

inline fun <reified T> getObject(
    key: String,
    arguments: Bundle?,
    savedInstanceState: Bundle?
): T? {
    var result: T? = checkInArguments<T>(arguments, key)

    if (result == null) {
        result = checkInFragmentSavedInstanceState<T>(savedInstanceState, key)
    }

    return result
}

inline fun <reified T> checkInFragmentSavedInstanceState(
    savedInstanceState: Bundle?,
    key: String
): T? {
    if (savedInstanceState != null) {
        val obj = savedInstanceState.get(key)
        if (obj != null) {
            if (obj is T) {
                return obj
            }
        }
    }
    return null
}

inline fun <reified T> checkInArguments(
    arguments: Bundle?,
    key: String
): T? {
    if (arguments != null) {
        val obj = arguments.get(key)
        if (obj != null) {
            if (obj is T) {
                return obj
            }
        }
    }
    return null
}
