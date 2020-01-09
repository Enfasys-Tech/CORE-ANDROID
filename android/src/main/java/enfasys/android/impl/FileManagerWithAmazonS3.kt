package enfasys.android.impl

import android.content.Context
import android.graphics.Bitmap
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import enfasys.android.core.FileManager
import enfasys.android.core.Logger
import enfasys.android.core.NetworkVerifier
import enfasys.android.core.TimeManager
import enfasys.android.core.usecase.FailureDescription
import enfasys.android.core.usecase.NoFileAvailableError
import enfasys.android.core.usecase.Result
import enfasys.android.impl.extensions.getAbsolutePath
import id.zelory.compressor.Compressor
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManagerWithAmazonS3 @Inject constructor(
    private val networkVerifier: NetworkVerifier,
    private val logger: Logger,
    private val amazonS3Credentials: AmazonS3Credentials,
    private val appContext: Context,
    private val timeManager: TimeManager
) : FileManager {
    private val amazonS3Client: AmazonS3Client

    data class AmazonS3Credentials(
        val cognitoIdentityPoolId: String,
        val cognitoRegion: String,
        val s3Region: String,
        val s3Bucket: String
    )

    init {
        val credentialsProvider = CognitoCachingCredentialsProvider(
            appContext,
            amazonS3Credentials.cognitoIdentityPoolId,
            Regions.fromName(amazonS3Credentials.cognitoRegion)
        )
        amazonS3Client = AmazonS3Client(
            credentialsProvider,
            Region.getRegion(amazonS3Credentials.s3Region)
        )
    }

    override fun upload(path: String, key: String): Result<String> {
        return try {
            val file = File(path)
            if (!file.exists()) {
                return Result.fail(NoFileAvailableError)
            }
            val objectRequest = PutObjectRequest(amazonS3Credentials.s3Bucket, key, file)
            objectRequest.cannedAcl = CannedAccessControlList.PublicRead
            amazonS3Client.putObject(objectRequest)
            val url = amazonS3Client.getResourceUrl(amazonS3Credentials.s3Bucket, key)
            Result.success(url)
        } catch (ex: Exception) {
            if (networkVerifier.isConnectionAvailable()) {
                logger.e(ex)
                Result.fail(ex)
            } else {
                Result.fail(FailureDescription.NetworkConnectionError)
            }
        }
    }

    override fun compressImageAndDeleteTheOldOne(file: File, quality: Int): File {
        val compressedFileName = timeManager.getTime().toString()

        val compressedFile = Compressor(appContext)
            .setQuality(quality)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setDestinationDirectoryPath(appContext.getAbsolutePath())
            .compressToFile(file, "${compressedFileName}.jpg")

        if (file.exists()) {
            file.delete()
        }

        return compressedFile
    }

    override fun createNewImage(): File {
        val name = timeManager.getTime().toString()
        return File(appContext.getAbsolutePath(), "${name}.jpg")
    }
}
