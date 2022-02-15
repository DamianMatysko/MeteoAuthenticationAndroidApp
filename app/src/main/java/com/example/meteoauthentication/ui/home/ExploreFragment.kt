package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "ExploreFragment"

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var binding: FragmentExploreBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val listFragment = ListFragment()
    private val grafFragment = GrafFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        binding = FragmentExploreBinding.bind(view)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val fragmentAdapter = TabFragmentAdapter(parentFragmentManager)


        fragmentAdapter.addFragment(listFragment, "List")
        fragmentAdapter.addFragment(grafFragment, "Graf")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        getStationsName()
        return view
    }
    
    private fun getStationsName() {
        viewModel.getUserStationsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val spinner: Spinner = binding.stationSpinner
                        val arrayList: ArrayList<String> = ArrayList()
                        val stationsArrayList = it.value
                        for (i in stationsArrayList) {
                            arrayList.add(i.title)
                        }
                        val arrayAdapter = context?.let { it1 ->
                            ArrayAdapter(
                                it1,
                                android.R.layout.simple_spinner_item,
                                arrayList
                            )
                        }

                        spinner.adapter = arrayAdapter

                        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                listFragment.setStationId(stationsArrayList[position].id)
                                grafFragment.setStationId(stationsArrayList[position].id)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
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


}