package com.example.meteoauthentication.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        binding = FragmentHomeBinding.bind(view)
        getUserStations()
        initRecyclerView(view)


        val purple = context?.let { ContextCompat.getColor(it,R.color.purple_500) }
        binding.AddButton.backgroundTintList = purple?.let { ColorStateList.valueOf(it) }

        binding.AddButton.setOnClickListener{
            val dialog = AddStationDialogFragment.newInstance()
            dialog.show(parentFragmentManager, "addStationDialog")
        }
        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.stationsRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = StationRecyclerAdapter(arrayList, this)
        recyclerView.adapter = adapter
        Log.d(TAG, "getUserStations: $arrayList")
    }

    private fun getUserStations() {
        viewModel.getUserStationsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        adapter?.updateList(it.value)

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
        }
        viewModel.getUserStations()
    }

    override fun clickedPostItem(getUserStationResponse: GetUserStationResponse) {
        viewModel.setSelectedUserStation(getUserStationResponse)

        Log.d(TAG, "clickedPostItem: ${getUserStationResponse.id} ")
        val bundle = bundleOf("title" to getUserStationResponse.id.toInt())

        findNavController().navigate(R.id.stationDetailFragment, bundle)
    }

}