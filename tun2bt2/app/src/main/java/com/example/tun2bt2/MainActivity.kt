package com.example.tun2bt2

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val resultText = findViewById<TextView>(R.id.resultText)
        val checkButton = findViewById<Button>(R.id.checkButton)

        checkButton.setOnClickListener {
            val email = emailInput.text.toString().trim()

            when {
                email.isEmpty() -> {
                    resultText.setTextColor(Color.RED)
                    resultText.text = "Email không hợp lệ"
                }
                !email.contains("@") -> {
                    resultText.setTextColor(Color.RED)
                    resultText.text = "Email không đúng định dạng"
                }
                else -> {
                    resultText.setTextColor(Color.parseColor("#008000")) // Màu xanh
                    resultText.text = "Bạn đã nhập email hợp lệ"
                }
            }
        }
    }
}