package com.alfastack.sampleapp.threads

import com.alfastack.sampleapp.models.Employee
import com.alfastack.sampleapp.models.Flow

/**
 * Created by Joro on 31/12/2019
 */
internal class JsonDecoder(private val flow: Flow, private val lock: Object) : Runnable {
    override fun run() {
        synchronized(lock) {
            while (flow.response == null) {
                lock.wait()
            }
            val items = flow.response
            items?.let {
                for (i in 0 until items.length()) {
                    val item = items.getJSONObject(i)
                    val id = item.getString("id")
                    val name = item.getString("employee_name")
                    val salary = item.getString("employee_salary")
                    val age = item.getString("employee_age")
                    val employee = Employee(id, name, salary, age)
                    flow.employees.add(employee)
                }
                lock.notify()
            }
        }
    }
}