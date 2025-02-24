package com.example.kolbtestapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kolbtestapp.adapter.PostAdapter
import com.example.kolbtestapp.model.PostModel
import com.example.kolbtestapp.service.RetrofitClient
import com.example.kolbtestapp.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private lateinit var fetchButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var resultTextView: TextView
    private lateinit var postAdapter: PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchButton = findViewById(R.id.fetchButton)
        recyclerView = findViewById(R.id.recyclerView)

        postAdapter = PostAdapter(emptyList())
        recyclerView.adapter = postAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.randomButton).setOnClickListener {
            fetchRandomPost()
        }

        findViewById<Button>(R.id.fetchButton).setOnClickListener {
            fetchPosts()
        }
    }

    private fun fetchPosts() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.getPosts().enqueue(object : Callback<List<PostModel>> {
            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        postAdapter.updatePosts(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                handleFailure(t)
            }
        })

    }

    private fun fetchRandomPost() {
        val randomId = Random.nextInt(1, 101) // ID aleatório entre 1 e 100
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        apiService.getPostById(randomId).enqueue(object : Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    postAdapter.updatePosts(listOf(post!!))
                }
            }

            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                handleFailure(t)
            }
        })
    }

    private fun handleFailure(t: Throwable) {
        resultTextView.text = "Erro: ${t.message}"
        Log.e("ERRO", "Erro: ${t.message}")
    }
}