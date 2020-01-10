package enfasys.android.impl.extensions

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import enfasys.android.core.usecase.FailureDescription
import enfasys.android.impl.BaseTest
import kotlinx.coroutines.CancellationException
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AlertDialogExtensionsTest : BaseTest() {
    @Test
    fun `getMessageFromFailureDescription is not null when FailureDescription is not CancellationException`() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val message = getMessageFromFailureDescription(
            context,
            FailureDescription.NetworkConnectionError
        )

        assertNotNull(message)
    }

    @Test
    fun `getMessageFromFailureDescription is null when FailureDescription is not CancellationException`() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val message = getMessageFromFailureDescription(
            context,
            FailureDescription.WithThrowable(CancellationException())
        )

        assertNull(message)
    }
}
