package com.example.androidapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidapp.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var userViewModel = UserViewModel()

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

        val sharedPreference = this.activity?.getSharedPreferences("NOTES_PREFERENCES", Context.MODE_PRIVATE)

        view.findViewById<TextView>(R.id.loginButton).setOnClickListener {
            val email = view.findViewById<TextInputEditText>(R.id.editTextEmail).text.toString()
            val password = view.findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()

            lifecycleScope.launch{
                val isUserExistPromise = async {
                    userViewModel.findByEmailAndPassword(email, password)
                }
                val isUserExist = isUserExistPromise.await()

                if(isUserExist !== null){
                    sharedPreference?.edit()?.putString("username", isUserExist.username)
                        ?.commit()
                    val navigate = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(navigate)
                }else {
                    Toast.makeText(context, "no user found", Toast.LENGTH_SHORT).show()
                }
            }


        }

        view.findViewById<TextView>(R.id.goToRegister).setOnClickListener {
            val navigate = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(navigate)
        }
    }
}