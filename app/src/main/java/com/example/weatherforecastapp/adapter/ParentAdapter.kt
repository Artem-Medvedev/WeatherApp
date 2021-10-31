package com.example.weatherforecastapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.entity.WeatherObject

class ParentAdapter: RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    private var weatherList = mapOf<String, List<WeatherObject>>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var timeTextView: TextView? = null
        var childRecyclerView: RecyclerView? = null

        init {
            timeTextView = itemView.findViewById(R.id.textView5)
            childRecyclerView = itemView.findViewById(R.id.child_list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.timeTextView?.text = weatherList.keys.toList()[position]

        val adapter = WeatherListAdapter()
        holder.childRecyclerView?.adapter = adapter
        weatherList[weatherList.keys.toList()[position]]?.let { adapter.setData(it) }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    fun setData(notes: Map<String, List<WeatherObject>>) {
        this.weatherList = notes
        notifyDataSetChanged()
    }


}
