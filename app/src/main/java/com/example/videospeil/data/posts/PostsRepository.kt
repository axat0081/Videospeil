package com.example.videospeil.data.posts

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.videospeil.model.PostResults
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val dataRef: DatabaseReference
) {
    private var posts = MutableLiveData<MutableList<PostResults.Posts>>()
    fun getPosts(): MutableLiveData<MutableList<PostResults.Posts>> {
        dataRef.child("posts").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val set = HashSet<PostResults.Posts>()
                for (snapshot in dataSnapshot.children) {
                    set.add(
                        PostResults.Posts(
                            posterName = snapshot.child("posterName").value.toString(),
                            message = snapshot.child("message").value.toString(),
                            creationDate = snapshot.child("creationDate").value.toString()
                        )
                    )
                }
                val t = mutableListOf<PostResults.Posts>()
                t.addAll(set)
                posts.value = t
            }
        })
        return posts
    }

    suspend fun insertPosts(post: PostResults.Posts, context: Context) {
        val key = dataRef.child("posts").push().key
        dataRef.child("posts").child(key!!).setValue(post).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Post Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error: " + task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}