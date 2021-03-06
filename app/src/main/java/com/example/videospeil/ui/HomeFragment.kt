package com.example.videospeil.ui

//import com.onesignal.OneSignal
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        if (mAuth.currentUser == null) {
            sendUserToLogin()
        }

        //OneSignal.setSubscription(true)
        val dataRef = FirebaseDatabase.getInstance().reference.child("Users")
        val mAuth = FirebaseAuth.getInstance()
        /*      OneSignal.idsAvailable { userId, _ ->
                  dataRef.child(mAuth.currentUser!!.uid).child("NotificationKey").setValue(userId)
              }
              OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)*/
        binding.apply {
            gamesListButton.setOnClickListener {
                sendUserToGameLists()
            }
            favouritesButton.setOnClickListener {
                sendUserToFavourites()
            }
            seePostsButton.setOnClickListener {
                sendUserToPosts()
            }
            createPostsButton.setOnClickListener {
                sendUserToCreatePosts()
            }
             videoGalleryButton.setOnClickListener {
                sendUserToVideos()
            }
            uploadVideosButton.setOnClickListener {
                sensUserToUploadVideoActivity()
            }
            logoutButton.setOnClickListener {
                mAuth.signOut()
                sendUserToLogin()
            }
        }
        FirebaseInstanceId.getInstance().instanceId
    }

    private fun sensUserToUploadVideoActivity() {
        val intent = Intent(requireContext(), UploadVideoActivity::class.java)
        startActivity(intent)
    }

    private fun sendUserToVideos() {
        val action = HomeFragmentDirections.actionHomeFragmentToVideoGalleryFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToLogin() {
        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToGameLists() {
        val action = HomeFragmentDirections.actionHomeFragmentToGamesListFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToFavourites() {
        val action = HomeFragmentDirections.actionHomeFragmentToFavouritesFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToPosts() {
        val action = HomeFragmentDirections.actionHomeFragmentToPostsFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToCreatePosts() {
        val action = HomeFragmentDirections.actionHomeFragmentToCreatePostsFragment()
        findNavController().navigate(action)
    }
}