package com.example.androidapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidapp.entity.Note
import com.example.androidapp.entity.User
import com.example.androidapp.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var userViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.registerButton).setOnClickListener {
            val userName = view.findViewById<TextInputEditText>(R.id.editTextUsername).text.toString()
            val email = view.findViewById<TextInputEditText>(R.id.editTextEmail).text.toString()
            val password = view.findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()
            val passwordConfirmation = view.findViewById<TextInputEditText>(R.id.editTextPasswordConfirmation).text.toString()
            Log.d("input", "$userName $email $password $passwordConfirmation")


            userViewModel.insertUser(User(0,userName, email, password))

            val navigate = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(navigate)

        }

        view.findViewById<TextView>(R.id.goToLogin).setOnClickListener {
            val navigate = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(navigate)
        }
    }
}