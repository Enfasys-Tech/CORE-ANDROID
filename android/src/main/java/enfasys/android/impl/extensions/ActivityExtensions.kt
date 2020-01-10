package enfasys.android.impl.extensions

import android.content.Intent
import android.os.Bundle

inline fun <reified T> getObject(
    key: String,
    intent: Intent,
    savedInstanceState: Bundle?
): T? {
    var result: T? = checkInIntent<T>(intent, key)

    if (result == null) {
        result = checkInActivitySavedInstanceState<T>(savedInstanceState, key)
    }

    return result
}

inline fun <reified T> checkInActivitySavedInstanceState(
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

inline fun <reified T> checkInIntent(
    intent: Intent,
    key: String
): T? {
    val extras = intent.extras
    if (extras != null) {
        val obj = extras.get(key)
        if (obj != null) {
            if (obj is T) {
                return obj
            }
        }
    }
    return null
}
