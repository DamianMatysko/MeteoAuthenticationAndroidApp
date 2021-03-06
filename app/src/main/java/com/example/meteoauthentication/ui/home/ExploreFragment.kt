package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentExploreBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private val viewModel by viewModels<HomeViewModel>()
    private val listFragment = ListFragment()
    private val chartFragment = ChartFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        binding = FragmentExploreBinding.bind(view)

        getStationsName()

        initView()

        return view
    }


    private fun getStationsName() {
        viewModel.getMeasuredValuesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    listFragment.setStationMeasuredValues(it.value)
                    chartFragment.setStationMeasuredValues(it.value)
                }
                else -> {}
            }
        }



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

                        arrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
                        spinner.adapter = arrayAdapter

                        spinner.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    viewModel.getMeasuredValuesById(stationsArrayList[position].id)
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

    private fun initView() {
        viewPager = binding.viewPager
        setupViewPager(viewPager)
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = viewPagerAdapter.mFragmentTitleList[position]
        }.attach()
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        viewPagerAdapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle
        )
        viewPagerAdapter.addFragment(listFragment, "list")
        viewPagerAdapter.addFragment(chartFragment, "chart")
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 1
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        val mFragmentTitleList: MutableList<String> = ArrayList()

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getItemCount(): Int {
            return mFragmentList.size
        }
    }
}
