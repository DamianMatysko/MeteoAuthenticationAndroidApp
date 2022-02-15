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
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.meteoauthentication.R
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.databinding.FragmentLoginBinding
import com.example.meteoauthentication.ui.home.HomeActivity
import com.example.meteoauthentication.ui.startNewActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.google.android.gms.common.api.ApiException


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


        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveToken(
                            it.value.jwt
                        )
                        viewModel.saveEmail(
                            binding.editEmail.text.toString().trim()
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
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("294783079570-2r2nvbf3efafklbsrhp5p47ukr2si94j.apps.googleusercontent.com")
            //android      .requestIdToken("294783079570-q0sd94c5aqdu162ac040qdr2s8h7jo2u.apps.googleusercontent.com")
            .requestEmail()
            .build()


        val mGoogleSignInClient =
            activity?.let { GoogleSignIn.getClient(it.applicationContext, gso) };
        // todo Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.


        //updateUI(account)

        binding.googleSignInButton.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            if (account == null) {
                val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
                //  ActivityAuthBinding.startActivityForResult(signInIntent, RC_SIGN_IN)
            } else{
                account.idToken?.let { viewModel.oauthSignIn(it) }
            }
            Log.d(TAG, "onCreateView: ${account?.idToken.toString()}")
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //viewModel.oauthSignIn("465456")

            Log.d(TAG, "onActivityResult: ${account.idToken}")
            account.idToken?.let { viewModel.oauthSignIn(it) }
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode + "     " + e)
            //updateUI(null)
        }
    }

    private fun login() {
        val username = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        viewModel.login(username, password)
    }

    private fun googleSignin() {


    }
}