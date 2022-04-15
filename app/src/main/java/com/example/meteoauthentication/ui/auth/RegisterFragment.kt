package com.example.meteoauthentication.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        val loginFragment = LoginFragment();
        binding.toLoginTextView.setOnClickListener {
            Toast.makeText(context, "Login form", Toast.LENGTH_SHORT).show()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.activityFragmentContainer, loginFragment)?.commit()
        }

        binding.buttonRegister.setOnClickListener {
            register()
        }

        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.activityFragmentContainer, loginFragment)?.commit()
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onViewCreated: Failure $it")
                    if (it.errorCode == 401) {
                        Toast.makeText(context, "Failure please check form", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Error: ${it.errorCode}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                else -> {}
            }
        }
    }

    private fun register() {
        val username = binding.editEmail.text.toString().trim()
        val password = binding.editPasswordFirst.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        viewModel.register(username, password, email)
    }
}