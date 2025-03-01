package com.project.virtualdatabooks.UI

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.project.virtualdatabooks.MainActivity
import com.project.virtualdatabooks.R

class NotifactionUpdate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_notify_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val headerMessage = intent.getStringExtra("HEADER_MESSAGE")
        val subtitleMessage = intent.getStringExtra("SUBTITLE_MESSAGE")

        val headerTextView = findViewById<TextView>(R.id.tv_header)
        val subtitleTextView = findViewById<TextView>(R.id.tv_subtitle)

        headerTextView.text = headerMessage ?: "Terimakasih!"
        subtitleTextView.text = subtitleMessage ?: "Terimakasih, form anda telah diterima sistem!"

        val backButton = findViewById<MaterialButton>(R.id.notifyBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}