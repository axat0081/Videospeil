package com.example.videospeil.data.posts

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.videospeil.model.Comments
import com.example.videospeil.model.PostResults
import com.example.videospeil.notifications.SendNotifications
import com.google.firebase.database.*
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
                    val list = ArrayList<Comments>()
                    for (comments in snapshot.child("commentsList").children) {
                        list.add(
                            Comments(
                                commenterName = comments.child("commenterName").value.toString(),
                                comment = comments.child("comment").value.toString()
                            )
                        )
                    }
                    set.add(
                        PostResults.Posts(
                            id = snapshot.child("id").value.toString(),
                            posterId = snapshot.child("posterId").value.toString(),
                            posterName = snapshot.child("posterName").value.toString(),
                            message = snapshot.child("message").value.toString(),
                            creationDate = snapshot.child("creationDate").value.toString(),
                            commentsList = list
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
        val key = post.id
        Log.d("Creating", post.posterId)
        dataRef.child("posts").child(key).setValue(post).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Post Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error: " + task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    suspend fun insertComment(id: String, comments: Comments, context: Context, posterId: String) {
        val key = dataRef.child("posts").child(id).child("commentsList").push().key
        dataRef.child("posts").child(id).child("commentsList")
            .child(key!!).setValue(comments).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Comment sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "Error: " + task.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        FirebaseDatabase.getInstance().reference.child("Users").child(posterId)
            .child("NotificationKey").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val notifyKey = snapshot.value.toString()
                    SendNotifications(
                        message = "Someone has commented on your post",
                        heading = "New Comment",
                        key = notifyKey
                    )
                }
            })
    }

    fun getCommentList(id: String): MutableLiveData<List<Comments>> {
        Log.d("Score", id)
        val list = ArrayList<Comments>()
        dataRef.child("posts").child(id).child("commentsList")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        Log.d("Score", "Collecting")
                        list.add(
                            Comments(
                                commenterName = snapshot.child("commenterName").value.toString(),
                                comment = snapshot.child("comment").value.toString()
                            )
                        )
                    }
                }
            })
        Log.d("ScoreRepo", list.size.toString())
        val result = MutableLiveData<List<Comments>>()
        result.value = list
        return result
    }
}