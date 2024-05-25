package com.example.androidapp.component

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidapp.R
import com.example.androidapp.dataStore.DataStoreManager
import com.example.androidapp.entity.User
import com.example.androidapp.viewModel.DataStoreViewModel
import com.example.androidapp.viewModel.DataStoreViewModelFactory
import com.example.androidapp.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var userViewModel = UserViewModel()
    private lateinit var dataStoreViewModel: DataStoreViewModel
    private lateinit var pref: DataStoreManager

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

            pref = DataStoreManager(requireContext())
            dataStoreViewModel = ViewModelProvider(this, DataStoreViewModelFactory(pref))[DataStoreViewModel::class.java]

            registerHandler(view, dataStoreViewModel)

            val navigate =
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(navigate)

        }

        view.findViewById<TextView>(R.id.goToLogin).setOnClickListener {
            val navigate =
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(navigate)
        }
    }

    private fun registerHandler(view:View, dataStoreViewModel:DataStoreViewModel){
        val userName = view.findViewById<TextInputEditText>(R.id.editTextUsername).text.toString()
        val email = view.findViewById<TextInputEditText>(R.id.editTextEmail).text.toString()
        val password = view.findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()
        val passwordConfirmation = view.findViewById<TextInputEditText>(R.id.editTextPasswordConfirmation).text.toString()
        Log.d("input", "$userName $email $password $passwordConfirmation")


        userViewModel.insertUser(User(0, userName, email, password))

        val newUser =
            userViewModel.findByEmailAndPassword(email, password)

        if (newUser != null) {
            lifecycleScope.launch {
                val task = async { dataStoreViewModel.saveDataStore(newUser.uid) }
                task.await()
            }

        }
    }
}