package com.example.weatherforecastapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.views.MainActivity

class WeatherListAdapter: RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    private var weatherListAdapter = emptyList<WeatherObject>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var timeTextView: TextView? = null
        var descTextView: TextView? = null
        var tempTextView: TextView? = null
        var weatherImageView: ImageView? = null

        init {
            timeTextView = itemView.findViewById(R.id.mainTextView)
            descTextView = itemView.findViewById(R.id.timeTextView)
            tempTextView = itemView.findViewById(R.id.tempTextView)
            weatherImageView = itemView.findViewById(R.id.iconImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_element,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  currentItem = weatherListAdapter[position]
        holder.timeTextView?.text = currentItem.date
        holder.descTextView?.text = currentItem.desc
        holder.tempTextView?.text = currentItem.temp.toInt().toString()+"\u2103"
        if(currentItem.desc=="Clear"){
            holder.weatherImageView?.load(R.drawable.ic_light_mode_black_24dp)
        }
        if(currentItem.desc=="Clouds"){
            holder.weatherImageView?.load(R.drawable.cloud)
        }
        if(currentItem.desc=="Rain"||currentItem.desc=="Thunderstorm"){
            holder.weatherImageView?.load(R.drawable.raining)
        }

    }

    override fun getItemCount(): Int {
        return weatherListAdapter.size
    }

    fun setData(notes: List<WeatherObject>) {
        this.weatherListAdapter = notes
        notifyDataSetChanged()
    }


}
