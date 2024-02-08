package ru.dev.gamedev.honest_investor.repository

import android.content.Context
import androidx.work.*
import androidx.work.OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dev.gamedev.honest_investor.workers.ChunkedDownloadListenableWorker
import ru.dev.gamedev.honest_investor.workers.addDownloadTag
import ru.dev.gamedev.honest_investor.workers.downloadAttempt
import ru.dev.gamedev.honest_investor.workers.downloadProgress
import ru.dev.gamedev.honest_investor.workers.downloadWorkInfoListFlow
import ru.dev.gamedev.honest_investor.workers.idAsString
import ru.dev.gamedev.honest_investor.workers.outputFilename
import ru.dev.gamedev.honest_investor.workers.result
import ru.dev.gamedev.honest_investor.workers.stateAsString
import ru.dev.gamedev.honest_investor.workers.urlString
import ru.dev.gamedev.honest_investor.workers.workDownloadData
import java.io.File

class DownloadRepository(
    private val context: Context,
    private val workManager: WorkManager
) : Repository<Downloadable, List<Download>> {

    /**
     * Добавляю новый объект
     * */
    override fun adds(t: Downloadable) {
        createRequest<ChunkedDownloadListenableWorker>(
            urlString = t.urlString,
            outputFilename = t.outputFilename)
            .let { request ->
                workManager.enqueue(request)
            }
    }

    /**
     * Наблюдаю за ним
     * */
    override fun observe(): Flow<List<Download>> =
        workManager.downloadWorkInfoListFlow()
            .map { infoList ->
                infoList.map { info ->
                    Download(
                        id = info.idAsString(),
                        urlString = info.urlString(),
                        outputFilename = info.outputFilename(),
                        progress = info.downloadProgress(),
                        downloadAttempt = info.downloadAttempt(),
                        result = info.result(),
                        state = info.stateAsString())
                }
            }

    fun clean() {
        workManager.pruneWork()
    }

    private inline fun <reified T : ListenableWorker> createRequest(
        urlString: String,
        outputFilename: String
    ): WorkRequest =
        OneTimeWorkRequestBuilder<T>()
            .setInputData(workDownloadData(
                downloadUrl = urlString,
                outputFilename = File(context.filesDir, outputFilename).absolutePath))
            .setConstraints(Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .build())
            .addDownloadTag()
            .setExpedited(RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
}