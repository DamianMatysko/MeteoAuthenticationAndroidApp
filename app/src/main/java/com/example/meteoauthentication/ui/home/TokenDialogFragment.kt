package com.example.meteoauthentication.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.meteoauthentication.R
import com.example.meteoauthentication.databinding.FragmentTokenDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TokenDialogFragment : DialogFragment() {
    private lateinit var token1: String
    private lateinit var binding: FragmentTokenDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_token_dialog, container, false)
        binding = FragmentTokenDialogBinding.bind(view)
        binding.tokenTextView.text = token1
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.copyToClipboardButton.setOnClickListener{
            val myClipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", token1)
            myClipboard.setPrimaryClip(clipData)
        }
        return view
    }

    companion object {
        private const val TOKEN = "token"
        fun newInstance(
            token: String
        ): TokenDialogFragment = TokenDialogFragment().apply {
            arguments = Bundle().apply {
                token1 = getString(TOKEN, token).toString()
            }
        }
    }

}