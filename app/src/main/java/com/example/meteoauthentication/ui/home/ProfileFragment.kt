package com.example.meteoauthentication.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentProfileBinding
import com.example.meteoauthentication.ui.logout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.buttonLogout.setOnClickListener {
            logout()
        }

        binding.buttonUpdateUser.setOnClickListener {
            update()
        }
        viewModel.updateResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
        )
    }

    private fun update() {
        val newUsername = binding.editUsername.text.toString().trim()
        val newPassword = binding.editPassword.text.toString().trim()
        val newEmail = binding.editEmail.text.toString().trim()
        val newCity = binding.editCity.text.toString().trim()

        viewModel.updateUser(newUsername, newPassword, newEmail, newCity)
    }




}