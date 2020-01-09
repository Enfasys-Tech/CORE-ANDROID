package enfasys.android.impl.extensions

import android.content.Context
import enfasys.android.core.usecase.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import enfasys.android.impl.R
import kotlinx.coroutines.CancellationException

internal fun getMessageFromFailureDescription(
    context: Context,
    failureDescription: FailureDescription
): String? {
    if (failureDescription is FailureDescription.WithThrowable && failureDescription.throwable is CancellationException) {
        return null
    }

    return when (failureDescription) {
        is FailureDescription.NetworkConnectionError -> context.getString(R.string.core_dialog_error_networkConnection)
        is FailureDescription.WithFeature -> failureDescription.message
        is FailureDescription.WithThrowable -> when (failureDescription.throwable) {
            is NoDataInServerResponseError -> context.getString(R.string.core_dialog_error_serverError)
            is NoMessageInServerResponseError -> context.getString(R.string.core_dialog_error_serverError)
            is NoTokenAvailableError -> context.getString(R.string.core_dialog_error_deviceError)
            is NoFileAvailableError -> context.getString(R.string.core_dialog_error_deviceError)
            else -> context.getString(R.string.core_dialog_error_generalError)
        }
    }
}

fun Context.showUnderstoodDialog(
    failureDescription: FailureDescription,
    onUnderstoodButton: () -> Unit = {}
) {
    val message = getMessageFromFailureDescription(this, failureDescription)

    if (message != null) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setTitle(R.string.core_dialog_error_title)
            .setMessage(getMessageFromFailureDescription(this, failureDescription))
            .setPositiveButton(R.string.core_dialog_button_understood) { dialog, _ ->
                dialog.dismiss()
                onUnderstoodButton()
            }
        dialog.show()
    }
}

fun Context.showRetryAndCancelDialog(
    failureDescription: FailureDescription,
    onRetryButton: () -> Unit = {},
    onCloseButton: () -> Unit = {}
) {
    val message = getMessageFromFailureDescription(this, failureDescription)

    if (message != null) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setTitle(R.string.core_dialog_error_title)
            .setMessage(getMessageFromFailureDescription(this, failureDescription))
            .setPositiveButton(R.string.core_dialog_button_retry) { dialog, _ ->
                dialog.dismiss()
                onRetryButton()
            }
            .setNegativeButton(R.string.core_dialog_button_close) { dialog, _ ->
                dialog.dismiss()
                onCloseButton()
            }
        dialog.show()
    }
}