package enfasys.android.core

internal class FakeBaseRepository(
    override val logger: Logger,
    override val networkVerifier: NetworkVerifier
) : BaseRepository

internal class FakeLogger : Logger {
    var wasCalled: Boolean = false

    override fun e(throwable: Throwable, tag: String?) {
        wasCalled = true
    }

    override fun d(message: String, tag: String?) = Unit
}

internal class FakeNetworkVerifier(private val shouldReturnTrue: Boolean) : NetworkVerifier {
    override fun isConnectionAvailable() = shouldReturnTrue
}
