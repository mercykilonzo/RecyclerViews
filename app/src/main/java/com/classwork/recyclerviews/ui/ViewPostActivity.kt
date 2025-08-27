package com.classwork.recyclerviews.ui


import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.classwork.recyclerviews.R
import com.classwork.recyclerviews.api.ApiClient
import com.classwork.recyclerviews.model.ApiInterface
import com.classwork.recyclerviews.model.Comment
import com.classwork.recyclerviews.viewmodel.PostsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPostActivity : AppCompatActivity() {
    private var postId = 0
    private lateinit var rvComments: RecyclerView
    val postViewModel : PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(intent.extras!=null){
            postId = intent.extras!!.getInt("POST_ID")
            Toast.makeText(this, "$postId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        rvComments = findViewById(R.id.main)
        rvComments.layoutManager = LinearLayoutManager(this)
        postViewModel.fetchPostById(postId)
        fetchComments()
        Toast.makeText(this, "Post ID: $postId", Toast.LENGTH_SHORT).show()
    }



    private fun fetchComments() {
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getCommentsForPost(postId)
        request.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val comments = response.body() ?: emptyList()
                    val commentAdapter = CommentRvAdapter(comments)
                    rvComments.adapter = commentAdapter
                } else {
                    Toast.makeText(this@ViewPostActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Toast.makeText(this@ViewPostActivity, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}