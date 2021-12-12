//package com.example.meteoauthentication.ui
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.meteoauthentication.R
//import com.example.meteoauthentication.model.LocalLoginRequest
//
//class LoginFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_login, container, false) //super.onCreateView(inflater, container, savedInstanceState)
//
//        val registerFragment = RegisterFragment()
//        val toRegisterFragment : TextView = view.findViewById(R.id.toRegisterTextView)
//        toRegisterFragment.setOnClickListener {
//          Toast.makeText(context, "Register form",Toast.LENGTH_SHORT).show()
////            childFragmentManager.beginTransaction().apply {
////                replace(R.id.main_container , registerFragment)
////                commit()
////            }
//            val transaction = fragmentManager?.beginTransaction()
//            transaction?.replace(R.id.main_container, registerFragment)?.commit()
//
//        }
//
//
//       val editEmail : EditText =view.findViewById(R.id.editEmail)
////        val editEmail = editEmail.text.toString()
//       val editPassword : EditText =view.findViewById(R.id.editPassword)
//       val buttonLogin : Button =view.findViewById(R.id.buttonLogin)
//
//        buttonLogin.setOnClickListener {
//          val  localLoginRequest  : LocalLoginRequest = LocalLoginRequest(editEmail.text.toString(), editPassword.text.toString())
//
//        }
//        return  view
//    }
//
//
//}