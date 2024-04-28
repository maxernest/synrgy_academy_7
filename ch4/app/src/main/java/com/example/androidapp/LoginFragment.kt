package com.example.androidapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<TextView>(R.id.loginButton).setOnClickListener {
            val navigate = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(navigate)
        }

        view.findViewById<TextView>(R.id.goToRegister).setOnClickListener {
        val navigate = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(navigate)
        }

        return view
    }
}