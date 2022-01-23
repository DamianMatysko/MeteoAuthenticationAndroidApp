package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentHomeBinding
import com.example.meteoauthentication.model.GetUserStationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), PostClickHandler {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var recyclerView: RecyclerView
    private var arrayList: ArrayList<GetUserStationResponse> = arrayListOf()
    private var adapter: StationRecyclerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initRecyclerView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        adapter = StationRecyclerAdapter(arrayList, this)
        recyclerView.adapter = adapter
        Log.d(TAG, "getUserStations: $arrayList")
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.stationsRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // getUserStations() private fun getUserStations() {
        viewModel.getUserStationsResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        //recyclerView.adapter = StationRecyclerAdapter(it.value, this)

                        arrayList.addAll(it.value)
                         Log.d(TAG, "inside observe: $arrayList")

                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        println("Success $it")
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                    println("Failure $it")
                }
                else -> {}
            }
        })
        viewModel.getUserStations()
    }

    override fun clickedPostItem(getUserStationResponse: GetUserStationResponse) {
        Log.d(TAG, "clickedPostItem: ")
    }

}