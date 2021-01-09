package com.example.videospeil.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentVideoDetailsBinding

class VideoDetailsFragment : Fragment(R.layout.fragment_video_details) {
    private var _binding: FragmentVideoDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<VideoDetailsFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVideoDetailsBinding.bind(view)
        val controller = MediaController(context)
        binding.apply {
            videoTitleTextView.text = args.video.title
            uploaderNameTextView.text = "By- " + args.video.uploader
            addedOnTextView.text = "Added on- " + args.video.addedOn
            videoView.setVideoPath(args.video.url)
            videoView.setMediaController(controller)
            controller.setAnchorView(videoView)
        }
    }
}