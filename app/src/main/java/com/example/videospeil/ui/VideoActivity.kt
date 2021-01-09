package com.example.videospeil.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.videospeil.databinding.ActivityVideoBinding
import com.example.videospeil.model.Videos
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.DateFormat
import javax.inject.Inject

class VideoActivity : AppCompatActivity() {
    private val CODE = 1
    private lateinit var binding: ActivityVideoBinding
    private var videoUri: Uri? = null
    private val rootRef = FirebaseDatabase.getInstance().reference
    private val dataRef = rootRef.child("Videos")
    private val storeRef = FirebaseStorage.getInstance().getReference("Videos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mediaController = MediaController(this)
        binding.apply {
            videoView.setMediaController(mediaController)
            videoView.start()
            uploadButton.setOnClickListener {
                uploadVideo()
            }
        }
    }

    private fun uploadVideo() {
        if (videoUri != null) {
            binding.progressBar.isVisible = true
            val pathRef =
                storeRef.child(System.currentTimeMillis().toString() + "." + getExt(videoUri!!))
            var uploadTask = pathRef.putFile(videoUri!!)
            val urlTask: Task<Uri> =
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Error: " + task.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    pathRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downLoadUri = task.result
                        binding.progressBar.isVisible = false
                        val video = Videos(
                            "DevTeam", downLoadUri, DateFormat.getDateTimeInstance()
                                .format(System.currentTimeMillis())
                        )
                        val key = dataRef.child("videos").push().key
                        dataRef.child("videos").child(key!!).setValue(video)
                    }else{
                        Toast.makeText(applicationContext,"Some error occurred",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun chooseVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CODE)
    }

    private fun getExt(uri: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE || resultCode == Activity.RESULT_OK || data != null
            || (data != null && data.getData() != null)
        ) {
            videoUri = data!!.data
            binding.videoView.setVideoURI(videoUri)
        }
    }
}