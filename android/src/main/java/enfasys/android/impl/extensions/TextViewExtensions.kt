package enfasys.android.impl.extensions

import android.content.res.Resources
import android.text.TextUtils
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat

fun TextView.setHTMLText(value: String, @StringRes resource: Int, resources: Resources) {
    val escapedText = TextUtils.htmlEncode(value)
    val formattedText: String = resources.getString(resource, escapedText)
    val actualText = HtmlCompat.fromHtml(formattedText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    text = actualText
}