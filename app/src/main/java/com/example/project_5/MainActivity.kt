package com.example.project_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var photoImageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.poke_pic)


        button.setOnClickListener{
            getPokeImageUrl()
            Glide.with(this)
                .load(photoImageUrl)
                .fitCenter()
                .into(imageView)
        }

    }

    private fun getPokeImageUrl(){
        val randomId = Random.nextInt(30)
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$randomId", object : JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("Poke","Error")



            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.d("poke","Success")
                val sprites = json.jsonObject.getJSONObject("sprites")
                val spriteUrls = listOf(
                    sprites.getString("front_default"),
                    sprites.getString("back_default"),
                    sprites.getString("front_shiny"),
                    sprites.getString("back_shiny")
                )
                photoImageUrl = spriteUrls[Random.nextInt(spriteUrls.size)]
            }





            }]

        }

    }


