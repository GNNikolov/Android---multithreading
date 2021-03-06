package com.alfastack.sampleapp.threads

import android.os.Looper
import com.alfastack.sampleapp.models.Employee
import com.alfastack.sampleapp.models.Flow
import org.json.JSONObject
import java.io.BufferedReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by Joro on 31/12/2019
 */

internal class DataFetcher(private val flow: Flow, private val mLock: Object) : Runnable {
    lateinit var onFinishedCallback: (data: List<Employee>) -> Unit
    lateinit var onPreFetched: () -> Unit

    companion object {
        private val api: String = "https://dummy.restapiexample.com/api/v1/employees"
    }

    private val handler = android.os.Handler(Looper.getMainLooper())
    private val url: URL = URL(api)

    override fun run() {
        synchronized(mLock) {
            handler.post{
                onPreFetched()
            }
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            val allText =
                httpsURLConnection.inputStream.bufferedReader().use(BufferedReader::readText)
            val json = JSONObject(allText)
            flow.response = json.getJSONArray("data")
            mLock.notify()
            mLock.wait()
            handler.post { onFinishedCallback(flow.employees) }
        }
    }

}