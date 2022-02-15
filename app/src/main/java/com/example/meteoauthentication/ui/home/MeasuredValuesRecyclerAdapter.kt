package com.example.meteoauthentication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoauthentication.databinding.MeasuredValueItemBinding
import com.example.meteoauthentication.model.MeasuredValue

class MeasuredValuesRecyclerAdapter(
    private val arrayList: ArrayList<MeasuredValue>,
    private val measuredValuesPostClickHandler: MeasuredValuesPostClickHandler
) : RecyclerView.Adapter<MeasuredValuesRecyclerAdapter.MeasuredValuesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasuredValuesViewHolder {
        return MeasuredValuesViewHolder(
            MeasuredValueItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MeasuredValuesViewHolder, position: Int) {
        val currentItem = arrayList[position]
        holder.measuredValues.text = currentItem.toString()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateList(newList:ArrayList<MeasuredValue>) {
        arrayList.clear()
        arrayList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MeasuredValuesViewHolder(binding: MeasuredValueItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        val measuredValues: TextView = binding.measuredValuesTextView //todo change to id or delete on click

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val measuredValueString = arrayList[bindingAdapterPosition]
            measuredValuesPostClickHandler.clickedMeasuredPostItem(measuredValueString)
        }
    }

}