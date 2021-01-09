package com.example.videospeil.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.webkit.MimeTypeMap
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.videospeil.databinding.ActivityUploadvideoBinding
import com.example.videospeil.model.Videos
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.DateFormat

class UploadVideoActivity : AppCompatActivity() {
    private val IMAGE_CODE = 1
    private val VIDEOCODE = 2
    private lateinit var binding: ActivityUploadvideoBinding
    private var videoUri: Uri? = null
    private val rootRef = FirebaseDatabase.getInstance().reference
    private val dataRef = rootRef.child("Videos")
    private var uri: Uri? = null
    private val storeRef = FirebaseStorage.getInstance().getReference("Videos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadvideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.uploadVideoButton.setOnClickListener {
            openFileChooser()
        }
        binding.uploadButton.setOnClickListener {
            binding.progressBar.isVisible = true
            uploadVideo()
        }
    }

    private fun uploadVideo() {
        val uploaderName = binding.uploaderNameEditText.text.toString()
        val videoTitle = binding.videoTitleEditText.text.toString()
        if (TextUtils.isEmpty(uploaderName) || TextUtils.isEmpty(videoTitle)) {
            Toast.makeText(
                applicationContext,
                "Video title and uploader name cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        } else if (uri == null) {
            Toast.makeText(applicationContext, "Please chose a video to upload", Toast.LENGTH_SHORT)
                .show()
        } else {
            val ref = storeRef.child(System.currentTimeMillis().toString() + "." + getExt(uri!!))
            val uploadTask = ref.putFile(uri!!)
            val urlTask =
                uploadTask.continueWithTask { ref.downloadUrl }.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val video = Videos(
                            userId = FirebaseAuth.getInstance().currentUser!!.uid,
                            title = videoTitle,
                            url = it.result.toString(),
                            uploader = uploaderName
                        )
                        val key = dataRef.child("Videos").push().key
                        dataRef.child("Videos").child(key!!).setValue(video)
                        binding.uploaderNameEditText.text.clear()
                        binding.videoTitleEditText.text.clear()
                        binding.progressBar.isVisible = false
                        Toast.makeText(applicationContext, "Video Uploaded", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Error: " + it.exception,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

    private fun getExt(uri: Uri): String {
        val contentResolver = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(contentResolver.getType(uri))!!
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, VIDEOCODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VIDEOCODE && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            uri = data.data
        }
    }
}