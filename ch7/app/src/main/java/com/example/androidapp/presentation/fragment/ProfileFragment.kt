package com.example.androidapp.presentation.fragment

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.androidapp.MainApplication
import com.example.androidapp.R
import com.example.androidapp.presentation.viewModel.DataStoreViewModel
import com.example.androidapp.presentation.viewModel.DataStoreViewModelFactory
import com.example.androidapp.presentation.viewModel.UserViewModel
import com.example.androidapp.presentation.viewModel.UserViewModelFactory
import com.example.androidapp.utils.PermissionUtils
import com.example.background.KEY_IMAGE_URI
import com.example.domain.BlurWorker
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private lateinit var dataStoreViewModel: DataStoreViewModel
    private lateinit var userViewModel : UserViewModel
    private lateinit var uri: Uri
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                handleImage(uri)
            }
        }
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result!=null){
                handleImage(result)
            }
        }

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialization()

        view.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            val profilePicture = view.findViewById<ImageView>(R.id.imageView)
            val drawable = profilePicture.drawable
            if(this.isAProfilePicture(drawable)){
                this.showDialog()
            }else{
                Log.e("drawable",drawable.toString())
                Toast.makeText(context, "already have a profile picture", Toast.LENGTH_SHORT).show()
                this.runWorker()
            }
        }

        view.findViewById<Button>(R.id.updateButton).setOnClickListener {
            this.updateButtonHandler(view)
        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            lifecycleScope.launch {
                val task = async { dataStoreViewModel.saveDataStore(-1, -1) }
                task.await()

                val navigate =
                    ProfileFragmentDirections.actionProfileFragment2ToLoginFragment()
                findNavController().navigate(navigate)
            }
        }
    }

    private fun runWorker(){
        val data: Data = Data.Builder()
            .putString(KEY_IMAGE_URI,uri.toString())
            .build()

        val blurWorker = OneTimeWorkRequest.Builder(BlurWorker::class.java)
            .setInputData(data)
            .build()
        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueue(blurWorker)

        workManager.getWorkInfoByIdLiveData(blurWorker.id)
            .observe(viewLifecycleOwner, Observer {
                if(it.state.isFinished){
                    val outputData = it.outputData
                    val bluredUri = outputData.getString(KEY_IMAGE_URI)
                    if (bluredUri != null) {
                        Log.e("Fragment",bluredUri)
                        this.handleImage(bluredUri.toUri())
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
                Manifest.permission.READ_MEDIA_IMAGES
            )
        ) {
            activity?.let {
                PermissionUtils.requestPermission(
                    it,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                    ),
                    PermissionUtils.REQUEST_CODE_GALERY
                )
            }
        } else {
            activity?.intent?.type = "image/*"
            galleryResult.launch("image/*")
        }
    }

    private fun handleImage(uri: Uri) {
        view?.let {
            Glide.with(this)
                .load(uri)
                .into(it.findViewById<ImageView>(R.id.imageView))
            this.uri = uri
        }

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

    private fun isAProfilePicture(drawable:Drawable):Boolean{
        return drawable is VectorDrawable
    }

    private fun updateButtonHandler(view:View){
        val username = view.findViewById<TextInputEditText>(R.id.editTextUsername).text.toString()
        val fullName = view.findViewById<TextInputEditText>(R.id.editTextFullName).text.toString()
        val birthDate = view.findViewById<TextInputEditText>(R.id.editTextBirthDate).text.toString()
        val address = view.findViewById<TextInputEditText>(R.id.editTextAddress).text.toString()

        dataStoreViewModel.getUser().observe(viewLifecycleOwner) {
            userViewModel.updateUser(it, username, fullName, birthDate, address)
        }
        val navigate =
            ProfileFragmentDirections.actionProfileFragment2ToHomeFragment()
        findNavController().navigate(navigate)
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_dialog)

        dialog.findViewById<Button>(R.id.cameraButton).setOnClickListener {
            this.openCamera()
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.galleryButton).setOnClickListener {
            this.openGallery()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun initialization(){
        (getActivity()?.applicationContext as MainApplication).applicationComponent.inject(this)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        dataStoreViewModel = ViewModelProvider(this, dataStoreViewModelFactory).get(DataStoreViewModel::class.java)

    }
}