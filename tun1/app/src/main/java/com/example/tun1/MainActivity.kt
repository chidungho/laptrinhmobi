package com.example.tun1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Gán dữ liệu cho UI
        val nameTextView = findViewById<TextView>(R.id.name)
        val locationTextView = findViewById<TextView>(R.id.location)
        val avatarImageView = findViewById<ImageView>(R.id.avatar)

        nameTextView.text = "Johan Smith"
        locationTextView.text = "California, USA"
        Glide.with(this)
            .load(R.drawable.ic_avatar) // hoặc đường dẫn ảnh
            .apply(RequestOptions.circleCropTransform())
            .into(avatarImageView)
    }
}