package enfasys.android.impl.extensions

import android.content.Context

fun Context.getAbsolutePath(): String {
    return filesDir.absolutePath
}
