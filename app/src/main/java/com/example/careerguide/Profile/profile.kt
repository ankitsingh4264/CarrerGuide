package com.example.careerguide.Profile

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.careerguide.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

const val CAMERA_REQUEST_CODE = 200
const val STORAGE_REQUEST_CODE = 400
const val IMAGE_PICK_GALLERY = 1000
const val IMAGE_PICK_CAMERA = 100

lateinit var imageURI: Uri
lateinit var cameraPermission: Array<String>
lateinit var storagePermission: Array<String>

class profile : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraPermission = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        button_upload.setOnClickListener {
            showImportImageDialog()
        }


    }

    private fun showImportImageDialog() {
        val items = arrayOf("Camera", "Gallery")
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle("Select Image")
        dialog.setItems(items) { p0, p1 ->
            if (p1 == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickCamera()
                }
            } else {
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    pickGallery()
                }
            }
        }
        dialog.create().show()
    }

    private fun checkCameraPermission(): Boolean {
        val result1 = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val result2 = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val result3 = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        return result1 and result2
    }

    private fun requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE)
    }

    private fun pickCamera() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "NewImage")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text")
        imageURI = context?.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )!!

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA)
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        requestPermissions(
            storagePermission,
            STORAGE_REQUEST_CODE
        )
    }

    private fun pickGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted and writeStorageAccepted) {
                        pickCamera()
                    } else {
                        Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()

                    }
                }
            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (writeStorageAccepted) {
                        pickGallery()
                    } else {
                        Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data!=null){
                imageURI= data.data!!

            }
            if (requestCode == STORAGE_REQUEST_CODE && data!=null){

                val imgbitmap=data.extras!!.get("data") as Bitmap
                imageURI=storeImage(imgbitmap)

            }
        }
    }


    private fun storeImage(image: Bitmap) : Uri {
        val root = Environment.getExternalStorageDirectory().absolutePath
        val myDir = File("$root/saved_images")
        myDir.mkdirs()

        val fname = UUID.randomUUID().toString()
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Uri.fromFile(file)

    }
}