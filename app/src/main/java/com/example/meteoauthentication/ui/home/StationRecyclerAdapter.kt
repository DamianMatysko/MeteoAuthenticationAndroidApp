package com.example.meteoauthentication.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoauthentication.databinding.ListItemBinding
import com.example.meteoauthentication.model.GetUserStationResponse

class StationRecyclerAdapter(
    private val arrayList: ArrayList<GetUserStationResponse>,
    private val clickHandler: PostClickHandler
) : RecyclerView.Adapter<StationRecyclerAdapter.StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        return StationViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val currentItem = arrayList[position]
        holder.stationName.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList:ArrayList<GetUserStationResponse>) {
        arrayList.clear()
        arrayList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class StationViewHolder(binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        val stationName: TextView = binding.stationName

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val currentStation = arrayList[bindingAdapterPosition]
            clickHandler.clickedPostItem(currentStation)
        }
    }

}