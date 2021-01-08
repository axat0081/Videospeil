package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentUploadVideoBinding
import com.google.firebase.storage.FirebaseStorage
import java.net.URI

class UpLoadVideoFragment : Fragment(R.layout.fragment_upload_video) {
    private var _binding: FragmentUploadVideoBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storeRef = FirebaseStorage.getInstance().getReference("Videos")
    }
    fun getExt(uri: URI){

    }
}