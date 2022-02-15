package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentUpdateDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_dialog, container, false)
        binding = FragmentUpdateDialogBinding.bind(view)
        viewModel.updateResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
        )
        return view
    }

    private lateinit var binding: FragmentUpdateDialogBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.buttonUpdateUser.setOnClickListener {
            update()
        }

    }

    private fun update() {
        val newUsername = binding.editUsername.text.toString().trim()
        val newPassword = binding.editPassword.text.toString().trim()
        val newEmail = binding.editEmail.text.toString().trim()
        val newCity = binding.editCity.text.toString().trim()

        viewModel.updateUser(newUsername, newPassword, newEmail, newCity)

    }

    companion object {
        private const val TAG = "UpdateDialogFragment"
        fun newInstance(): UpdateDialogFragment = UpdateDialogFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}