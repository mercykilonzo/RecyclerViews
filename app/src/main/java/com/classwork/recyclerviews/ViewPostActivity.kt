package com.classwork.recyclerviews


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPostActivity : AppCompatActivity() {
    var postId = 0
    lateinit var tvPostTitle: TextView
    lateinit var tvPostBody: TextView
    lateinit var rvComments: RecyclerView
    lateinit var commentsAdapter: CommentsRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Check if the intent contains any extras
        if (intent.extras != null) {
            postId = intent.extras!!.getInt("POST_ID")
            Toast.makeText(this, "Post ID: $postId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        tvPostTitle = findViewById(R.id.tvPostTitle)
        tvPostBody = findViewById(R.id.tvPostBody)
        rvComments = findViewById(R.id.rvComments)
        rvComments.layoutManager = LinearLayoutManager(this)
        commentsAdapter = CommentsRvAdapter(this, listOf())
        rvComments.adapter = commentsAdapter

        fetchPostById()
        fetchComments()
    }

    fun displayPost(post: Post) {
        tvPostTitle.text = post.title
        tvPostBody.text = post.body
    }

    fun displayComments(comments: List<Comment>) {
        commentsAdapter.comments = comments
        commentsAdapter.notifyDataSetChanged()
    }

    fun fetchPostById() {
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getPostById(postId)
        request.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    displayPost(response.body()!!)
                } else {
                    Toast.makeText(baseContext, response.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchComments() {
        val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)
        apiClient.getCommentsByPostId(postId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val comments = response.body() ?: listOf()
                    commentsAdapter.comments = comments
                    commentsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@ViewPostActivity, "Failed to load comments", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Toast.makeText(this@ViewPostActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}