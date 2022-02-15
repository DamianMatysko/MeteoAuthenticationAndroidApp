package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentListBinding
import com.example.meteoauthentication.databinding.FragmentRegisterBinding
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

    private var stationId by Delegates.observable(0) { property, oldValue, newValue ->
        Log.d(TAG, "New Value $newValue")
        Log.d(TAG, "Old Value $oldValue")
        viewModel.getMeasuredValuesById(newValue)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        viewModel.getMeasuredValuesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "it: ${it.value.toString()}")
                    arrayList.clear()
                    arrayList = it.value
                    initRecyclerView(view)
                }
            }
        }

        return view
    }

    fun setStationId(id: Number) {
        stationId = id.toInt()
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.stationsRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MeasuredValuesRecyclerAdapter(arrayList, this)
        recyclerView.adapter = adapter
        Log.d(TAG, "initRecyclerView: $arrayList")
    }

    override fun clickedMeasuredPostItem(measuredValue: MeasuredValue) {
        TODO("Not yet implemented")
        //        viewModel.setSelectedUserStation(getUserStationResponse)
        //        Log.d(TAG, "clickedPostItem: ${getUserStationResponse.id} ")
        //        val bundle = bundleOf("title" to getUserStationResponse.id.toInt())
        //        findNavController().navigate(R.id.stationDetailFragment, bundle)
    }

}