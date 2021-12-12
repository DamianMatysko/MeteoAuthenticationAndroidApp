//package com.example.meteoauthentication.ui
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.meteoauthentication.R
//
//class RegisterFragment : Fragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_register, container, false) //super.onCreateView(inflater, container, savedInstanceState)
//
//        val loginFragment = LoginFragment()
//        val toRegisterFragment : TextView = view.findViewById(R.id.toLoginTextView)
//        toRegisterFragment.setOnClickListener {
//            Toast.makeText(context, "Login form", Toast.LENGTH_SHORT).show()
////            childFragmentManager.beginTransaction().apply {
////                replace(R.id.main_container , registerFragment)
////                commit()
////            }
//            val transaction = fragmentManager?.beginTransaction()
//            transaction?.replace(R.id.main_container, loginFragment)?.commit()
//
//        }
//        return  view
//    }
//
//}