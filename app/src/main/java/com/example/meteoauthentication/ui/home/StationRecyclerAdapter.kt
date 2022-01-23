package com.example.meteoauthentication.ui.home

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
                true
            )
        )
//        val itemView =
//            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
//        return StationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val currentItem = arrayList[position]
        holder.stationName.text = currentItem.title


//        holder.itemView.setOnClickListener(object  :View.OnClickListener{
//            override fun onClick(v: View?) {
//                val activity = v!!.context as AppCompatActivity
//            }
//        })

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    //    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val stationName: TextView = itemView.findViewById(R.id.stationName)
//    }
    inner class StationViewHolder(binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        val stationName: TextView = binding.stationName //itemView.findViewById(R.id.stationName)

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val currentStation = arrayList[bindingAdapterPosition]
            clickHandler.clickedPostItem(currentStation)
        }
    }
}