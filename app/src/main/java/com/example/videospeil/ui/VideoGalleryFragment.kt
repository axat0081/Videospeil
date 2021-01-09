package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.adapter.VideosAdapter
import com.example.videospeil.data.videos.VideosViewModel
import com.example.videospeil.databinding.FragmentVideoGalleryBinding
import com.example.videospeil.model.Videos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoGalleryFragment : Fragment(R.layout.fragment_video_gallery),
    VideosAdapter.OnItemClickListener {
    private var _binding: FragmentVideoGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<VideosViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVideoGalleryBinding.bind(view)
        val videosAdapter = VideosAdapter(this)
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                adapter = videosAdapter
                itemAnimator = null
            }
        }
        viewModel.videosList.observe(viewLifecycleOwner) {
            videosAdapter.submitList(it)
        }
    }

    override fun onItemCLick(video: Videos) {
        sendUserToVideoDetails(video)
    }

    private fun sendUserToVideoDetails(video: Videos) {
        val actions = VideoGalleryFragmentDirections.actionVideoGalleryFragmentToVideoDetailsFragment2(video)
        findNavController().navigate(actions)
    }
}