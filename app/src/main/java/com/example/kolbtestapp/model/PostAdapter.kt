package com.example.kolbtestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kolbtestapp.model.PostModel
import com.example.kolbtestapp.R

class PostAdapter(private var posts: List<PostModel>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.postTitle)
        private val body: TextView = view.findViewById(R.id.postBody)

        fun bind(post: PostModel) {
            title.text = post.title
            body.text = post.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    fun updatePosts(newPosts: List<PostModel>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}

