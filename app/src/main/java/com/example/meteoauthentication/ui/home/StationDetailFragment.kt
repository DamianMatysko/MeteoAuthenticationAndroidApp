package com.example.meteoauthentication.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentStationDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class StationDetailFragment : Fragment(R.layout.fragment_station_detail) {
    private lateinit var binding: FragmentStationDetailBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var id: Number

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_station_detail, container, false)
        binding = FragmentStationDetailBinding.bind(view) //todo check

        viewModel.getStationAuthorizationResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val dialog = TokenDialogFragment.newInstance(it.value.jwt)
                        dialog.show(parentFragmentManager, "tokenDialog")
                    }
                }
                else -> {}
            }
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSelectedUserStation()?.let {
            id = it.id
            binding.titleTextView.text = it.title
            binding.destinationTextView.text = it.destination
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            val date = dateFormat.parse(it.registration_time)
            val formatter = SimpleDateFormat("dd. MM. yyyy")
            val dateStr = formatter.format(date)
            binding.registrationTimeTextView.text = dateStr.toString()
            binding.modelDescriptionTextView.text = it.model_description

        }
        binding.generateTokenButton.setOnClickListener {
            getStationToken(id)
        }
        binding.deleteStation.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, _ ->
                    deleteStation(this.id)
                    dialog.dismiss()
                    parentFragmentManager.popBackStack()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }

    private fun getStationToken(id: Number) {
        viewModel.getStationToken(id)
    }

    private fun deleteStation(id: Number) {
        viewModel.deleteStation(id)
    }
}