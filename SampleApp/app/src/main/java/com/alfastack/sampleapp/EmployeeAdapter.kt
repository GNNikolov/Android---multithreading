package com.alfastack.sampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alfastack.sampleapp.models.Employee

/**
 * Created by Joro on 01/01/2020
 */
class EmployeeAdapter(private val data: List<Employee>) : RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder>() {

    inner class EmployeeHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Employee) {
            view.findViewById<ImageView>(R.id.iconPerson).setColorFilter(ContextCompat.getColor(view.context, R.color.colorPrimary))
            view.findViewById<TextView>(R.id.name).text = item.name
            view.findViewById<TextView>(R.id.age).text = String.format("Age: %s", item.age)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_list_item, parent, false)
        return EmployeeHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        val employee = data.get(position)
        holder.bind(employee)
    }
}