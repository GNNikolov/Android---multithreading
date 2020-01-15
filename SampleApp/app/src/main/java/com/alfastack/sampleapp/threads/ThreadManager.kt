package com.alfastack.sampleapp.threads

import androidx.annotation.GuardedBy
import com.alfastack.sampleapp.models.Employee
import com.alfastack.sampleapp.models.Flow
import java.util.concurrent.Executors

/**
 * Created by Joro on 01/01/2020
 */
class ThreadManager {
    private val lock = Object()
    @GuardedBy("lock")
    private val flow = Flow()
    private val dataFetchRunnable = DataFetcher(flow, lock)
    private val jsonDecodeRunnable = JsonDecoder(flow, lock)

    companion object {
        private const val WORKING_THREADS = 2
    }

    private val threadPool = Executors.newFixedThreadPool(WORKING_THREADS)

    fun setOnDataProcessedCallback(onFinished: (data: List<Employee>) -> Unit) {
        dataFetchRunnable.onFinishedCallback = onFinished
    }
    fun setOnPreFetchedCallback(onPreFetched: () -> Unit) {
        dataFetchRunnable.onPreFetched = onPreFetched
    }

    fun run() {
        threadPool.apply {
            submit(dataFetchRunnable)
            submit(jsonDecodeRunnable)
        }
    }

    fun shutDown() {
        if (!threadPool.isShutdown) {
            threadPool.shutdown()
        }
    }

}