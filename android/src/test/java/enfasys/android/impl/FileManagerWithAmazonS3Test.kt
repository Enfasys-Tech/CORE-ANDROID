package enfasys.android.impl

import androidx.test.core.app.ApplicationProvider
import enfasys.android.core.FileManager
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FileManagerWithAmazonS3Test : BaseTest() {
    private val fixedTime = 322L

    @Test
    fun `Returns file with the current time as its name`() {
        val fileManager: FileManager =
            FileManagerWithAmazonS3(
                FakeNetworkVerifier(false),
                FakeLogger(),
                FileManagerWithAmazonS3.AmazonS3Credentials(
                    "",
                    "us-east-1",
                    "us-east-1",
                    "bucket"
                ),
                ApplicationProvider.getApplicationContext(),
                FakeTimeManager(fixedTime)
            )

        val file = fileManager.createNewImage()

        assertEquals(file.name, "${fixedTime}.jpg")
        assertTrue(file.absolutePath.endsWith("${fixedTime}.jpg"))
    }
}