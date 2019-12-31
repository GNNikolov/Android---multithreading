package com.alfastack.sampleapp.controler

import android.os.Looper
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by Joro on 31/12/2019
 */

class DataFetcher(private val textView: TextView, private val flow: com.alfastack.sampleapp.models.Flow, private val mLock: Object) : Runnable {
    companion object{
        private val api: String = "https://dummy.restapiexample.com/api/v1/employees"
    }
    private val handler = android.os.Handler(Looper.getMainLooper())
    private val url: URL = URL(api)

    override fun run() {
        synchronized(mLock) {
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            val allText = httpsURLConnection.inputStream.bufferedReader().use(BufferedReader::readText)
            val array = JSONArray(allText)
            flow.response = array
            mLock.notify()
            mLock.wait()
            handler.post{
                textView.text = flow.employees.toString()
            }
        }
    }

}