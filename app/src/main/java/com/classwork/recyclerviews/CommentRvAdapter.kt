package com.classwork.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CommentsRvAdapter(val context: Context, var comments: List<Comment>) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_list_item, parent, false)
        return CommentsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val currentComment = comments[position]
        holder.tvCommentName.text = currentComment.name
        holder.tvCommentEmail.text = currentComment.email
        holder.tvCommentBody.text = currentComment.body
    }
}

class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCommentName = itemView.findViewById<TextView>(R.id.tvCommentName)
    val tvCommentEmail = itemView.findViewById<TextView>(R.id.tvCommentEmail)
    val tvCommentBody = itemView.findViewById<TextView>(R.id.tvCommentBody)
    val cvComment = itemView.findViewById<CardView>(R.id.cvComment)
}