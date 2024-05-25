package com.example.androidapp.component

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.androidapp.BlurWorker
import com.example.androidapp.PermissionUtils
import com.example.androidapp.R
import com.example.androidapp.dataStore.DataStoreManager
import com.example.androidapp.viewModel.DataStoreViewModel
import com.example.androidapp.viewModel.DataStoreViewModelFactory
import com.example.androidapp.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

class ProfileFragment : Fragment() {
    private lateinit var dataStoreViewModel: DataStoreViewModel
    private lateinit var pref: DataStoreManager
    private var userViewModel = UserViewModel()
    private lateinit var uri: Uri
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                handleImage(uri)
            }
        }
    private val galeryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result!=null){
                handleImage(result)
            }
        }


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

        runWorker()

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

    private fun runWorker(){
        val data: Data = Data.Builder()
            .putString("inputData","this is inputData")
            .build()

        val blurWorker = OneTimeWorkRequest.Builder(BlurWorker::class.java)
            .setInputData(data)
            .build()
        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueue(blurWorker)

        workManager.getWorkInfoByIdLiveData(blurWorker.id)
            .observe(viewLifecycleOwner, Observer {
                if(it.state.isFinished){
                    val data = it.outputData
                    val message = data.getString("outputData")
                    if (message != null) {
                        Log.e("Fragment",message)
                    }
                }
            })
    }

    private fun openCamera() {
        if (!PermissionUtils.isPermissionGranted(requireContext(), Manifest.permission.CAMERA)) {
            activity?.let {
                PermissionUtils.requestPermission(
                    it,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PermissionUtils.REQUEST_CODE_CAMERA
                )
            }
        } else {
            //JALANKAN LOGIC
            val photoFile = File.createTempFile(
                "IMG_",
                ".jpg",
                getActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )

            uri = FileProvider.getUriForFile(
                requireContext(),
                "${getActivity()?.packageName}.provider",
                photoFile
            )
            cameraResult.launch(uri)
        }
    }

    private fun openGallery() {
        if (!PermissionUtils.isPermissionGranted(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            activity?.let {
                PermissionUtils.requestPermission(
                    it,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PermissionUtils.REQUEST_CODE_GALERY
                )
            }
        } else {
            activity?.intent?.type = "image/*"
            galeryResult.launch("image/*")
        }
    }

    private fun handleImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into("need to implement")

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.REQUEST_CODE_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    "Camera Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (requestCode == PermissionUtils.REQUEST_CODE_GALERY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    "Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}