package com.example.meteoauthentication.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentLoginBinding
import com.example.meteoauthentication.ui.home.HomeActivity
import com.example.meteoauthentication.ui.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        val registerFragment = RegisterFragment()
        binding.toRegisterTextView.setOnClickListener{
            Toast.makeText(context, "Register form", Toast.LENGTH_SHORT).show()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.activityFragment, registerFragment)?.commit()
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
//                        viewModel.saveAccessTokens(
//                            it.value.user.access_token!!,
//                            it.value.user.refresh_token!!
//                        )
                        viewModel.saveToken(
                            it.value.jwt
                        )


                        println("xxxxxxxxxxxxxxxxxxxSuccess")

                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }

                }
              is Resource.Failure -> println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyFailure ${it}") // todo  handleApiError(it) { login() }
            }
        })

//        binding.editPassword.addTextChangedListener {
//            val email = binding.editEmail.text.toString().trim()
//            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
//        }

        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        viewModel.login(username, password)
    }
}