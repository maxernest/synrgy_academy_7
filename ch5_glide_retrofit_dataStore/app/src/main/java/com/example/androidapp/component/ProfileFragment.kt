package com.example.androidapp.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidapp.R
import com.example.androidapp.dataStore.DataStoreManager
import com.example.androidapp.viewModel.DataStoreViewModel
import com.example.androidapp.viewModel.DataStoreViewModelFactory
import com.example.androidapp.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var dataStoreViewModel: DataStoreViewModel
    private lateinit var pref: DataStoreManager
    private var userViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = DataStoreManager(requireContext())
        dataStoreViewModel = ViewModelProvider(this, DataStoreViewModelFactory(pref))[DataStoreViewModel::class.java]

        view.findViewById<Button>(R.id.updateButton).setOnClickListener {
            val username = view.findViewById<TextInputEditText>(R.id.editTextUsername).text.toString()
            val fullName = view.findViewById<TextInputEditText>(R.id.editTextFullName).text.toString()
            val birthDate = view.findViewById<TextInputEditText>(R.id.editTextBirthDate).text.toString()
            val address = view.findViewById<TextInputEditText>(R.id.editTextAddress).text.toString()

            dataStoreViewModel.getDataStore().observe(viewLifecycleOwner) {
                userViewModel.updateUser(it, username, fullName, birthDate, address)
            }
            val navigate =
                ProfileFragmentDirections.actionProfileFragment2ToHomeFragment()
            findNavController().navigate(navigate)

        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            lifecycleScope.launch {
                val task = async { dataStoreViewModel.saveDataStore(-1) }
                task.await()

                val navigate =
                    ProfileFragmentDirections.actionProfileFragment2ToLoginFragment()
                findNavController().navigate(navigate)
            }


        }
    }
}