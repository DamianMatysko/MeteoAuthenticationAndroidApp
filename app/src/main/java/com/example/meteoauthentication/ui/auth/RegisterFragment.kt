package com.example.meteoauthentication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentLoginBinding
import com.example.meteoauthentication.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

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
            transaction?.replace(R.id.activityFragment, loginFragment)?.commit()
        }

        binding.buttonRegister.setOnClickListener {
            register()
        }

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.activityFragment, loginFragment)?.commit()
                }
                is Resource.Failure -> println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyFailure ${it}")
            }
        })
    }

    private fun register() {
        val username = binding.editEmail.text.toString().trim()
        val password = binding.editPasswordFirst.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        viewModel.register(username, password, email)
    }
}