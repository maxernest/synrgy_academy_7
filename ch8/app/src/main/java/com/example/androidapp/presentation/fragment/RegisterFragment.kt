package com.example.androidapp.presentation.fragment

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
import com.example.androidapp.MainApplication
import com.example.androidapp.R
import com.example.androidapp.data.local.entity.User
import com.example.androidapp.presentation.viewModel.DataStoreViewModel
import com.example.androidapp.presentation.viewModel.DataStoreViewModelFactory
import com.example.androidapp.presentation.viewModel.UserViewModel
import com.example.androidapp.presentation.viewModel.UserViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterFragment : Fragment() {

    private lateinit var userViewModel : UserViewModel
    private lateinit var dataStoreViewModel: DataStoreViewModel

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    @Inject
    lateinit var dataStoreViewModelFactory: DataStoreViewModelFactory

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

        this.initialization()

        view.findViewById<TextView>(R.id.registerButton).setOnClickListener {

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

    private fun registerHandler(view:View, dataStoreViewModel: DataStoreViewModel){
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
                val task = async { dataStoreViewModel.saveDataStore(newUser.uid, newUser.accountId) }
                task.await()
            }

        }
    }

    private fun initialization(){
        (getActivity()?.applicationContext as MainApplication).applicationComponent.inject(this)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        dataStoreViewModel = ViewModelProvider(this, dataStoreViewModelFactory).get(DataStoreViewModel::class.java)
    }
}