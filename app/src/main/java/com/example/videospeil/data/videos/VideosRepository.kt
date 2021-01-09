package com.example.videospeil.data.videos

import androidx.lifecycle.MutableLiveData
import com.example.videospeil.model.Videos
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class VideosRepository @Inject constructor(
    private val dataRef: DatabaseReference
) {
    val result = MutableLiveData<MutableList<Videos>>()
    fun getVideos(): MutableLiveData<MutableList<Videos>> {
        val set = HashSet<Videos>()
        dataRef.child("Videos").child("Videos").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    set.add(
                        Videos(
                            userId = snapshot.child("userId").value.toString(),
                            uploader = snapshot.child("uploader").value.toString(),
                            url = snapshot.child("url").value.toString(),
                            addedOn = snapshot.child("addedOn").value.toString(),
                            title = snapshot.child("title").value.toString()
                        )
                    )
                }
                val videosList = mutableListOf<Videos>()
                videosList.addAll(set)
                result.value = videosList
            }

        })
        return result
    }
}