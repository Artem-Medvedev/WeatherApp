package com.example.weatherforecastapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.entity.WeatherObject

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


        holder.timeTextView?.text = currentItem.time.substring(0,5)
        holder.descTextView?.text = currentItem.desc
        holder.tempTextView?.text = currentItem.temp.toInt().toString()+"\u2103"
        if(currentItem.desc=="Clear"){
            if(currentItem.time.substring(0,2).toInt()>=20 || currentItem.time.substring(0,2).toInt()<=3) {
                holder.weatherImageView?.load(R.drawable.moon__1_)
            } else {
                holder.weatherImageView?.load(R.drawable.sun__2_)
            }
        }
        if(currentItem.desc=="Clouds"){
            if(currentItem.time.substring(0,2).toInt()>=20 || currentItem.time.substring(0,2).toInt()<=3) {
                holder.weatherImageView?.load(R.drawable.moon)
            } else {
                holder.weatherImageView?.load(R.drawable.cloud)
            }
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
