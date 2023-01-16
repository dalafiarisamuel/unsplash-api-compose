package ng.devtamuno.unsplash.compose.file

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FileDownloaderImpl @Inject constructor(private val context: Context) : FileDownloader {

    override fun downloadFile(fileUrl: URL, targetFile: File) =
        flow {
            emit(download(fileUrl, targetFile))
        }

    override fun downloadImageFileToDownloadFolder(fileUrl: String) {
        val uri = Uri.parse(fileUrl)
        val fileName = "${uri.pathSegments[0]}.jpeg"

        val request = DownloadManager.Request(uri)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun download(fileUrl: URL, targetFile: File) {
        withContext(Dispatchers.IO) {
            try {
                fileUrl.openStream().use { input ->
                    FileOutputStream(targetFile).use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (t: Throwable) {
                throw FileDownloadException(t)
            }
        }
    }
}
