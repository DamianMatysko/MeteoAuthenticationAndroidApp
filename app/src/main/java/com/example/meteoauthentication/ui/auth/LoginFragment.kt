package com.example.meteoauthentication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentLoginBinding
import com.example.meteoauthentication.ui.home.HomeActivity
import com.example.meteoauthentication.ui.startNewActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()
    private val RC_SIGN_IN = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val registerFragment = RegisterFragment()
        binding.toRegisterTextView.setOnClickListener {
            Toast.makeText(context, "Register form", Toast.LENGTH_SHORT).show()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.activityFragmentContainer, registerFragment)?.commit()

        }

        binding.buttonLogin.setOnClickListener {
            login()
        }


        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value.jwt,
                            it.value.refreshToken
                        )
                        viewModel.saveEmail(
                            binding.editEmail.text.toString().trim()
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onViewCreated: Failure $it")
                    if (it.errorCode == 401) {
                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d(TAG, "onActivityResult: ${account.idToken}")
            account.idToken?.let { viewModel.oauthSignIn(it) }
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode + "     " + e)
        }
    }

    private fun login() {
        val username = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        viewModel.login(username, password)
    }

}
