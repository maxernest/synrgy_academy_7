package com.example.androidapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidapp.MainApplication
import com.example.androidapp.R
import com.example.androidapp.presentation.viewModel.DataStoreViewModel
import com.example.androidapp.presentation.viewModel.DataStoreViewModelFactory
import com.example.androidapp.presentation.viewModel.UserViewModel
import com.example.androidapp.presentation.viewModel.UserViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : Fragment() {

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
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialization()

        dataStoreViewModel.getUser().observe(viewLifecycleOwner) {
            if (it > -1) {
                val navigate =
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(navigate)
            }
        }

        view.findViewById<TextView>(R.id.loginButton).setOnClickListener {
            val email = view.findViewById<TextInputEditText>(R.id.editTextEmail).text.toString()
            val password = view.findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()

            loginHandler(email, password)
        }

        view.findViewById<TextView>(R.id.goToRegister).setOnClickListener {
            val navigate =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(navigate)
        }
    }

    private fun loginHandler(email:String, password:String){
        val isUserExistPromise =
            userViewModel.findByEmailAndPassword(email, password)

        if (isUserExistPromise !== null) {
            lifecycleScope.launch {
                val task = async { dataStoreViewModel.saveDataStore(isUserExistPromise.uid,isUserExistPromise.accountId) }
                task.await()
            }

            val navigate =
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(navigate)
        } else {
            Toast.makeText(context, "no user found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initialization(){
        (getActivity()?.applicationContext as MainApplication).applicationComponent.inject(this)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        dataStoreViewModel = ViewModelProvider(this, dataStoreViewModelFactory).get(DataStoreViewModel::class.java)
    }
}