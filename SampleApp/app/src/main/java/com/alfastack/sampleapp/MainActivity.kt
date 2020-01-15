package com.alfastack.sampleapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfastack.sampleapp.threads.ThreadManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var threadManager: ThreadManager
    private lateinit var list: RecyclerView
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        list = findViewById(R.id.list)
        message = findViewById(R.id.info)
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        list.visibility = View.GONE
        threadManager = ThreadManager()
        threadManager.setOnDataProcessedCallback {
            if (it.isNotEmpty()) {
                list.visibility = View.VISIBLE
                message.visibility = View.GONE
                list.adapter = EmployeeAdapter(it)
            }
        }
        threadManager.setOnPreFetchedCallback {
            message.text = "Loading..."
        }
        fab.setOnClickListener { view ->
            threadManager.run()
        }
    }

    override fun onDestroy() {
        threadManager.shutDown()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
