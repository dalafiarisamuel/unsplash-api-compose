package ng.devtamuno.unsplash.compose.file

import java.io.File
import java.net.URL
import kotlinx.coroutines.flow.Flow

interface FileDownloader {
    fun downloadFile(
        fileUrl: URL,
        targetFile: File
    ): Flow<Unit>

    fun downloadImageFileToDownloadFolder(
        fileUrl: String,
    )
}
