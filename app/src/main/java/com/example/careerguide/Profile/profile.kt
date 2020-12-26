package com.example.careerguide.Profile

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import com.example.careerguide.login.LoginFrag.Companion.phone
import com.example.careerguide.login.SignUpFragment.Companion.name
import com.example.careerguide.login.loginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

const val STORAGE_REQUEST_CODE = 400

var imageURI: Uri? = null
lateinit var auth : FirebaseAuth
private lateinit var profilemvvm: profileViewModel

class profile : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var use: Users = Users()
        auth = FirebaseAuth.getInstance()

        profilemvvm = ViewModelProvider(requireActivity()).get(profileViewModel::class.java)

        profilemvvm.usersDetails()
        profilemvvm.mUserDetail.observe(
            requireActivity()
        ) {
            edit_profile_name.setText(it.name)
            edit_profile_phone.setText(it.phone)
            edit_profile_headline.setText(it.headline)
            if (it.imagepath != null && it?.imagepath != "") {
                if (profile_image != null) {
                    Glide.with(requireContext()).load(it?.imagepath)
                        .into(profile_image!!)
                }
            }
        }
//        profilemvvm.usersDetails()
//        profilemvvm.mUserDetail.observe(requireActivity(),
//            {
//                edit_profile_name.text=it.name
//            }
//        )

        button_upload.setOnClickListener {
            showImportImageDialog()
        }

        button_save_changes.setOnClickListener {
            use = Users(
                name = edit_profile_name.text.toString(),
                phone = edit_profile_phone.text.toString(),
                headline = edit_profile_headline.text.toString()
            )

            profilemvvm.insertUser(use)

            if (imageURI!=null) {
                profilemvvm.uploadprofileToFirebase(imageURI,"imagepath")
            }
                Toast.makeText(
                requireActivity(),
                "Upload Successful",
                Toast.LENGTH_SHORT
            ).show()


        }
    }

    private fun showImportImageDialog() {
        if (takepermissions()){

            choosePhotofromGallery(STORAGE_REQUEST_CODE)
        }
    }

    private  fun takepermissions():Boolean{

        var check=false;
        Dexter.withContext(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport?) {
                if (multiplePermissionsReport!!.areAllPermissionsGranted()) {
                    check = true;
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Please provide permission to access this function",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                permissionToken: PermissionToken?
            ) {
                permissionToken!!.continuePermissionRequest()
            }

        }
        ).onSameThread().check()

        if (check) return true;
        return false;
    }

    private fun choosePhotofromGallery(requestCode: Int){
        val galleryintent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryintent, STORAGE_REQUEST_CODE)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.i("rajeev", "dasd")
            if (requestCode == STORAGE_REQUEST_CODE && data!=null){
                imageURI= data.data!!
                profile_image.setImageURI(imageURI)
            }
        }

    }


}