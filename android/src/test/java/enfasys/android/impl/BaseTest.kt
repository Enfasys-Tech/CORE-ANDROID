package enfasys.android.impl

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class, sdk = [28])
@RunWith(RobolectricTestRunner::class)
abstract class BaseTest