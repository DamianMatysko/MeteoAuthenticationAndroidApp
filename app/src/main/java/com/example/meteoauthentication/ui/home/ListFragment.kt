package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentListBinding
import com.example.meteoauthentication.model.MeasuredValue
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

private const val TAG = "ListFragment"

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), MeasuredValuesPostClickHandler {
    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var recyclerView: RecyclerView
    private var arrayList: ArrayList<MeasuredValue> = arrayListOf()
    private var adapter: MeasuredValuesRecyclerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)

        initRecyclerView(view)
        return view
    }

    fun setStationMeasuredValues(measuredValues: ArrayList<MeasuredValue>) {
        arrayList.clear()
        arrayList = measuredValues
        adapter?.updateList(arrayList)
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.stationsRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MeasuredValuesRecyclerAdapter(arrayList, this)
        recyclerView.adapter = adapter
        Log.d(TAG, "initRecyclerView: $arrayList")
    }

    override fun clickedMeasuredPostItem(measuredValue: MeasuredValue) {
        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        //        viewModel.setSelectedUserStation(getUserStationResponse)
        //        Log.d(TAG, "clickedPostItem: ${getUserStationResponse.id} ")
        //        val bundle = bundleOf("title" to getUserStationResponse.id.toInt())
        //        findNavController().navigate(R.id.stationDetailFragment, bundle)
    }

}