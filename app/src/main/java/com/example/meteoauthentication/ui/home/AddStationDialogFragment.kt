package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentAddStationDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStationDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddStationDialogBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_station_dialog, container, false)
        binding = FragmentAddStationDialogBinding.bind(view)
        viewModel.addStationResponse.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.buttonUpdateUser.setOnClickListener {
            add()
        }

    }

    private fun add() {
        val title = binding.editTitle.text.toString().trim()
        val destination = binding.editDestination.text.toString().trim()
        val modelDescription = binding.editModelDescription.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()

        viewModel.addStation(title, destination, modelDescription, phone.toLong())

    }

    companion object {
        fun newInstance(): AddStationDialogFragment = AddStationDialogFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}